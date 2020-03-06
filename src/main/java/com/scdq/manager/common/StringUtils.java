package com.scdq.manager.common;

/**
 * 字符串常用工具
 */
public class StringUtils {

    /**
     * 字符串非空检测
     * 1. 字符串为null时返回true
     * 2. 字符串为""是返回true
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 字符串非空检测
     *  1. 字符串为null时返回true
     *  2. 字符串为""时返回true
     *  3. 仅由空白字符组成的字符串返回true
     * @param str
     * @return
     */
    public static boolean isNullOrSpace(String str) {
        return str == null || str.trim().isEmpty();
    }
}
