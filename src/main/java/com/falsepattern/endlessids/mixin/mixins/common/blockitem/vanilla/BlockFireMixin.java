package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.block.BlockFire;

@Mixin(BlockFire.class)
public abstract class BlockFireMixin {
    @ModifyConstant(method = {"<init>", "rebuildFireInfo", "getFlammability", "getEncouragement"},
                    constant = @Constant(intValue = VanillaConstants.blockIDCount),
                    remap = false,
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.blockIDCount;
    }
}
