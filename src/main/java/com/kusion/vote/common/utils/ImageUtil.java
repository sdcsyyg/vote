package com.kusion.vote.common.utils;

public class ImageUtil {

//    public static final String PREVIEW = "data/upload";
    public static final String PREVIEW = "images/preview";

    public static String wrapAsAbsolutePath(String domain, String path) {
        return domain + PREVIEW + "/" + path;
    }

    public static String wrapAsRelativePath(String path) {
        return "/" + PREVIEW + "/" + path;
    }
}
