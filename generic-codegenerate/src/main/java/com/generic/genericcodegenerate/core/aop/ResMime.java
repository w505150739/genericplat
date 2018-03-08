package com.generic.genericcodegenerate.core.aop;

import java.util.HashMap;
import java.util.Map;

public class ResMime {

    private static final Map<String, String> map = new HashMap<String, String>();

    static {
        map.put("abs", "audio/x-mpeg");
        map.put("css", "text/css");
        map.put("js", "text/javascript");
        map.put("png", "image/png");
        map.put("jpg", "image/jpeg");
        map.put("swf", "application/x-shockwave-flash");
        map.put("xls", "application/vnd.ms-excel");
        map.put("gif", "image/gif"); }

    public static String get(String s) {
        return (String)map.get(s);
    }
}
