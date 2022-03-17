package com.lqh.wanandroid.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     description:
 *     Created by: Lqh
 *     date: 20211116
 *     update: 1116
 *     version:1.0
 * </pre>
 */

public class LauStringUtils {
    public static String removeAllBank(String str) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            s = m.replaceAll("");
        }
        return s;
    }

    public static String removeAllBank(String str, int count) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s{" + count + ",}|\t|\r|\n");
            Matcher m = p.matcher(str);
            s = m.replaceAll(" ");
        }
        return s;
    }
} 
 