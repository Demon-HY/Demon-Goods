package org.demon.utils.beans;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作类
 */
public class StringUtils {

    // 判断一个字符串是否都为数字
    private static final String NUMBER = "[0-9]+";

    // 判断一个字符串是否都为数字
    private static final Pattern PATTERN_NUMBER = Pattern.compile(NUMBER);

    private StringUtils() {
    }

    /**
     * 判断字符串是否为数字的方法
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否都为数字
     */
    public static boolean isDigit(String strNum) {
        Matcher matcher = PATTERN_NUMBER.matcher(strNum);
        return matcher.matches();
    }

    /**
     * 第一个字符大写
     *
     * @param str
     * @return
     */
    public static String uncapitalize(String str) {
        return org.apache.commons.lang.StringUtils.capitalize(str);
    }

    /**
     * 去除null以及空字符串
     *
     * @return:
     * @author: YZH
     */
    public static String clearBlank(String s) {
        if (s == null) {
            return "";
        } else {
            return s.trim().replace("null", "");
        }
    }

    /**
     * {将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名}
     *
     * @param s 原文件名
     * @return 重新编码后的文件名
     * @author: YZH
     */
    public static String toUtf8String(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c <= 255) {
                sb.append(c);
            } else {
                byte[] b;

                try {
                    b = Character.toString(c).getBytes("UTF-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 隐藏手机号中间4位
     */
    public static String hidePhoneNumber(String phoneNumber) {

        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }

    /**
     * 隐藏姓名，只显示姓
     */
    public static String hideFullname(String fullname) {
        if (fullname == null || "".equals(fullname)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(fullname.substring(0, 1));
        if (fullname.length() > 1) {
            for (int i = 1; i < fullname.length(); i++) {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    /**
     * List to String  a,b,c,...
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null || list.size() < 1) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : list) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 读取字符串第 index 次出现特定符号的位置
     *
     * @param string
     * @param index
     * @param character
     * @return
     */
    public static int getCharacterPosition(String string, int index, String character) {
        //这里是获取"/"符号的位置
        Matcher slashMatcher = Pattern.compile(character).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if (mIdx == index) {
                break;
            }
        }
        return slashMatcher.start();
    }
}


