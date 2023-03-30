package com.falsepattern.endlessids;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.constants.ExtendedConstants;

import net.minecraft.block.Block;

public class Hooks {
    public static int getIdFromBlockWithCheck(final Block block, final Block oldBlock) {
        final int id = Block.getIdFromBlock(block);
        if (GeneralConfig.catchUnregisteredBlocks && id == -1) {
            throw new IllegalArgumentException("Block " + block +
                                               " is not registered. <-- Say about this to the author of this mod, or you can try to enable \"RemoveInvalidBlocks\" option in EID config.");
        }
        if (id >= 0 && id <= ExtendedConstants.maxBlockID) {
            return id;
        }
        if (id == -1) {
            return Block.getIdFromBlock(oldBlock);
        }
        throw new IllegalArgumentException("id out of range: " + id);
    }

    public static void shortToByteArray(short[] shortArray, int shortOffset, byte[] byteArray, int byteOffset, int shortCount) {
        for (int i = 0; i < shortCount; i++) {
            short s = shortArray[shortOffset + i];
            byteArray[byteOffset + i * 2] = (byte) (s & 255);
            byteArray[byteOffset + i * 2 + 1] = (byte) ((s >>> 8) & 255);
        }
    }

    public static void byteToShortArray(byte[] byteArray, int byteOffset, short[] shortArray, int shortOffset, int byteCount) {
        for (int i = 0; i < byteCount; i++) {
            byte b = byteArray[byteOffset + i];
            shortArray[shortOffset + i / 2] = (short) (i % 2 == 0
                                                       ? (b & 255)
                                                       : (shortArray[shortOffset + i / 2] | ((b & 255) << 8)));
        }
    }

    public static byte[] shortToByteArray(short[] shortArray) {
        byte[] byteArray = new byte[shortArray.length * 2];
        shortToByteArray(shortArray, 0, byteArray, 0, shortArray.length);
        return byteArray;
    }

    public static short[] byteToShortArray(byte[] byteArray) {
        short[] shortArray = new short[(byteArray.length + 1) / 2];
        byteToShortArray(byteArray, 0, shortArray, 0, byteArray.length);
        return shortArray;
    }

    public static void byteToShortArrayLegacy(byte[] byteArray, short[] shortArray) {
        for (int i = 0; i < 256; ++i) {
            shortArray[i] = (short) (((byteArray[i + 256] << 8) & 255) | (byteArray[i] & 255));
        }
    }

    public static void scatter(byte[] byteArray, short[] shortArray) {
        for (int i = 0; i < byteArray.length; i++) {
            shortArray[i] = (short) (byteArray[i] & 0xff);
        }
    }
}
