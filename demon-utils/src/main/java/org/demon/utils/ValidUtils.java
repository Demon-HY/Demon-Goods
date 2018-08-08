package org.demon.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 有效验证
 * <p>
 * Created by Demon on 2017/7/22 0022.
 */
public class ValidUtils {

    private static Logger logger = LoggerFactory.getLogger(ValidUtils.class);

    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

    /**
     * 转码
     *
     * @param input
     * @return
     */
    public static String encodeURIComponent(String input) {
        if (isBlank(input)) {
            return input;
        }

        int l = input.length();
        StringBuilder o = new StringBuilder(l * 3);
        try {
            for (int i = 0; i < l; i++) {
                String e = input.substring(i, i + 1);
                if (!ALLOWED_CHARS.contains(e)) {
                    byte[] b = e.getBytes("utf-8");
                    o.append(getHex(b));
                    continue;
                }
                o.append(e);
            }
            return o.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return input;
    }

    /**
     * 解析对象，当对象为null时，返回空字符串
     *
     * @param obj
     * @return 空字符串
     */
    public static Object parseValue(Object obj) {
        if (obj == null) {
            return "";
        }

        return obj;
    }

    public static String parseString(Object obj) {
        if (obj == null) {
            return "";
        }

        return String.valueOf(obj);
    }

    /**
     * 解码
     *
     * @param encodedURI
     * @return
     */
    public static String decodeURIComponent(String encodedURI) {
        char actualChar;

        StringBuffer buffer = new StringBuffer();

        int bytePattern, sumb = 0;

        for (int i = 0, more = -1; i < encodedURI.length(); i++) {
            actualChar = encodedURI.charAt(i);

            switch (actualChar) {
                case '%': {
                    actualChar = encodedURI.charAt(++i);
                    int hb = (Character.isDigit(actualChar) ? actualChar - '0'
                            : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    actualChar = encodedURI.charAt(++i);
                    int lb = (Character.isDigit(actualChar) ? actualChar - '0'
                            : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
                    bytePattern = (hb << 4) | lb;
                    break;
                }
                case '+': {
                    bytePattern = ' ';
                    break;
                }
                default: {
                    bytePattern = actualChar;
                }
            }

            if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
                sumb = (sumb << 6) | (bytePattern & 0x3f);
                if (--more == 0) {
                    buffer.append((char) sumb);
                }
            } else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
                buffer.append((char) bytePattern);
            } else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
                sumb = bytePattern & 0x1f;
                more = 1;
            } else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
                sumb = bytePattern & 0x0f;
                more = 2;
            } else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
                sumb = bytePattern & 0x07;
                more = 3;
            } else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
                sumb = bytePattern & 0x03;
                more = 4;
            } else { // 1111110x
                sumb = bytePattern & 0x01;
                more = 5;
            }
        }
        return buffer.toString();
    }

    private static String getHex(byte[] buf) {
        StringBuilder o = new StringBuilder(buf.length * 3);
        for (int i = 0; i < buf.length; i++) {
            int n = buf[i] & 0xff;
            o.append("%");
            if (n < 0x10) {
                o.append("0");
            }
            o.append(Long.toString(n, 16).toUpperCase());
        }
        return o.toString();
    }

    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    /**
     * 判断某个对象是否为空 集合类、数组做特殊处理
     *
     * @param obj
     * @return 如为空，返回true,否则false
     * @author YZH
     */
    @SuppressWarnings("unchecked")
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }

        // 如果不为null，需要处理几种特殊对象类型
        if (obj instanceof String) {
            String _obj = (String) obj;
            return StringUtils.isBlank(_obj);
        } else if (obj instanceof Collection) {
            // 对象为集合
            Collection coll = (Collection) obj;
            return coll.size() == 0;
        } else if (obj instanceof Map) {
            // 对象为Map
            Map map = (Map) obj;
            return map.size() == 0;
        } else if (obj.getClass().isArray()) {
            // 对象为数组
            return Array.getLength(obj) == 0;
        } else {
            // 其他类型，只要不为null，即不为empty
            return false;
        }
    }

    /**
     * 判断多个字段是否有为空的，用于做请求参数校验
     *
     * @param objs 可变数组
     * @return true 是空
     */
    public static boolean isBlanks(Object... objs) {
        if (objs.length == 0) {
            return true;
        }

        for (Object obj : objs) {
            if (isBlank(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 去除null以及空字符串
     *
     * @return:
     * @author: YZH
     */
    public static String clearBlank(Object s) {
        if (s == null) {
            return "";
        } else {
            return s.toString().trim();
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
        // Matcher slashMatcher = Pattern.compile("/").matcher(string);
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

    /**
     * 判断字符串是否为数字的方法
     *
     * @param str
     * @return
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
     *
     * @param strNum
     * @return
     */
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

    /**
     * 判断对象是否为空,不包括: 空字符串,空数组
     * @param arg
     * @return
     */
    public static boolean isEmpty(Object arg) {
        return arg == null;
    }
}
