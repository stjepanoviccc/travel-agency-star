package com.sr182022.travelagencystar.utils;

public class EnumUtil {
    public static String formatEnumName(Enum<?> enumValue) {
        String enumName = enumValue.name();
        // Replace underscores with spaces
        return enumName.replace("_", " ");
    }
}