package com.generic.genericcodegenerate.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CodeStringUtils {

    public CodeStringUtils() {
    }

    public static String getStringSplit(String[] val) {
        StringBuffer sqlStr = new StringBuffer();
        String[] var5 = val;
        int var4 = val.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String s = var5[var3];
            if (StringUtils.isNotBlank(s)) {
                sqlStr.append(",");
                sqlStr.append("'");
                sqlStr.append(s.trim());
                sqlStr.append("'");
            }
        }

        return sqlStr.toString().substring(1);
    }

    public static String getInitialSmall(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(0, 1).toLowerCase() + str.substring(1);
        }

        return str;
    }

    public static Integer getIntegerNotNull(Integer t) {
        return t == null ? 0 : t;
    }

    public static boolean isIn(String substring, String[] source) {
        if (source != null && source.length != 0) {
            for(int i = 0; i < source.length; ++i) {
                String aSource = source[i];
                if (aSource.equals(substring)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isIn(String substring, List<String> ls) {
        String[] source = new String[0];
        if (ls != null) {
            source = (String[])ls.toArray();
        }

        if (source != null && source.length != 0) {
            for(int i = 0; i < source.length; ++i) {
                String aSource = source[i];
                if (aSource.equals(substring)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
}
