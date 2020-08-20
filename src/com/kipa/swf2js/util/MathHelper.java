package com.kipa.swf2js.util;

public class MathHelper {
    public static int makeSignedInt(int value, int bits) {
        return (value << (32 - bits)) >> (32 - bits);
    }
}
