package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.play.server.S24PacketBlockAction;

@Mixin(S24PacketBlockAction.class)
public abstract class S24PacketBlockActionMixin {
    @ModifyConstant(method = {"readPacketData", "writePacketData"},
                    constant = @Constant(intValue = VanillaConstants.blockIDMask),
                    require = 2)
    private int extend1(int constant) {
        return ExtendedConstants.blockIDMask;
    }
}
