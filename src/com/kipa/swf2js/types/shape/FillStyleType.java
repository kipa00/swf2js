package com.kipa.swf2js.types.shape;

public enum FillStyleType {
    SOLID(0x00),
    LINEAR_GRADIENT(0x10),
    RADIAL_GRADIENT(0x12),
    FOCAL_RADIAL_GRADIENT(0x13),
    REPEATING_BITMAP(0x40),
    CLIPPED_BITMAP(0x41),
    NONSMOOTHED_REPEATING_BITMAP(0x42),
    NONSMOOTHED_CLIPPED_BITMAP(0x43);

    private final int byteCode;

    FillStyleType(int value) {
        this.byteCode = value;
    }

    public int getByteCode() {
        return this.byteCode;
    }

    public static FillStyleType findByByteCode(int byteCode) {
        for (FillStyleType value: FillStyleType.values()) {
            if (value.byteCode == byteCode) {
                return value;
            }
        }
        return null;
    }
}
