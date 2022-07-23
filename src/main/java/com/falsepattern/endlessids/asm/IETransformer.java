package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.asm.transformer.ChunkProviderSuperPatcher;
import com.falsepattern.endlessids.asm.transformer.DevFixer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;

import net.minecraft.launchwrapper.IClassTransformer;

import java.util.Arrays;

public class IETransformer implements IClassTransformer {
    public static final Logger logger;
    public static boolean isObfuscated;

    static {
        logger = LogManager.getLogger(Tags.MODNAME + " ASM");
    }

    public IETransformer() {
        super();
    }

    public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ClassEdit edit = ClassEdit.get(transformedName);
        final ClassNode cn = new ClassNode(Opcodes.ASM5);
        final ClassReader reader = new ClassReader(bytes);
        reader.accept(cn, edit == null ? ClassReader.EXPAND_FRAMES : 0);
        if (ChunkProviderSuperPatcher.isChunkProvider(cn)) {
            if (edit == null) {
                edit = ClassEdit.ChunkProviderSuperPatcher;
            } else {
                ClassEdit.ChunkProviderSuperPatcher.getTransformer().transform(cn, isObfuscated);
            }
        }
        if (edit == null) {
            return isObfuscated ? bytes : DevFixer.fixDev(bytes);
        }
        if (!isObfuscated) {
            DevFixer.transform(cn);
        }
        IETransformer.logger.debug("Patching {} with {}...", transformedName, edit.getName());
        try {
            edit.getTransformer().transform(cn, IETransformer.isObfuscated);
        } catch (AsmTransformException t) {
            IETransformer.logger.error("Error transforming {} with {}: {}", transformedName, edit.getName(),
                                       t.getMessage());
            throw t;
        } catch (Throwable t2) {
            IETransformer.logger.error("Error transforming {} with {}: {}", transformedName, edit.getName(),
                                       t2.getMessage());
            throw new RuntimeException(t2);
        }
        final ClassWriter writer = new ClassWriter(edit == ClassEdit.ChunkProviderSuperPatcher ? ClassWriter.COMPUTE_FRAMES : 0);
        try {
            final ClassVisitor check = new CheckClassAdapter(writer);
            cn.accept(check);
        } catch (Throwable t3) {
            IETransformer.logger.error("Error verifying {} transformed with {}: {}", transformedName, edit.getName(),
                                       t3.getMessage());
            throw new RuntimeException(t3);
        }
        IETransformer.logger.debug("Patched {} successfully.", transformedName);
        return writer.toByteArray();
    }
}
