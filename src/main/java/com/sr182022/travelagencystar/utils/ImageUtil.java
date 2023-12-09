package com.sr182022.travelagencystar.utils;

import java.util.Base64;

public class ImageUtil {
    public static String convertToBase64(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);
    }
}
