package com.generic.genericcodegenerate.entity;

import org.apache.commons.lang.StringUtils;

public class TableConvert {
    public TableConvert() {
    }

    public static String getNullAble(String nullable) {
        if (!"YES".equals(nullable) && !"yes".equals(nullable) && !"y".equals(nullable) && !"Y".equals(nullable) && !"f".equals(nullable)) {
            return !"NO".equals(nullable) && !"N".equals(nullable) && !"no".equals(nullable) && !"n".equals(nullable) && !"t".equals(nullable) ? null : "N";
        } else {
            return "Y";
        }
    }

    public static String getNullString(String nullable) {
        return StringUtils.isBlank(nullable) ? "" : nullable;
    }

    public static String getV(String s) {
        return "'" + s + "'";
    }
}
