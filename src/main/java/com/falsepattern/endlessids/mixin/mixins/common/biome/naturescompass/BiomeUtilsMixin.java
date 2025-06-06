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

package com.falsepattern.endlessids.mixin.mixins.common.biome.naturescompass;

import com.chaosthedude.naturescompass.util.BiomeUtils;
import com.falsepattern.endlessids.PlaceholderBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = BiomeUtils.class,
       remap = false)
public abstract class BiomeUtilsMixin {
    @Inject(method = "biomeIsBlacklisted",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void blockPlaceholders(BiomeGenBase biome, CallbackInfoReturnable<Boolean> cir) {
        if (biome instanceof PlaceholderBiome) {
            cir.setReturnValue(true);
        }
    }
}
