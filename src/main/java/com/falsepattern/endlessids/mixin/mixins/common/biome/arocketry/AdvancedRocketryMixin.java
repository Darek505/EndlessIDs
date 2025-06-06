/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.biome.arocketry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import zmaster587.advancedRocketry.AdvancedRocketry;

@Mixin(value = AdvancedRocketry.class,
       remap = false)
public abstract class AdvancedRocketryMixin {
    @ModifyConstant(method = "load",
                    constant = {@Constant(intValue = 110, ordinal = 0),
                                @Constant(intValue = 111, ordinal = 0),
                                @Constant(intValue = 112, ordinal = 0),
                                @Constant(intValue = 113, ordinal = 0),
                                @Constant(intValue = 114, ordinal = 0),
                                @Constant(intValue = 115, ordinal = 0),
                                @Constant(intValue = 116, ordinal = 0),
                                @Constant(intValue = 117, ordinal = 0),
                                @Constant(intValue = 118, ordinal = 0),
                                @Constant(intValue = 119, ordinal = 0)},
                    require = 10)
    private int shiftBiomeIDsUp(int constant) {
        return constant + 9000;
    }

    @ModifyConstant(method = "preInit",
                    constant = @Constant(intValue = -2),
                    require = 1)
    private int changeDefaultDimID(int constant) {
        return -5613;
    }
}
