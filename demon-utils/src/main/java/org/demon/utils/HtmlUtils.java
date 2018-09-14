package org.demon.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by demon on 2017/7/1 0001.
 */
public class HtmlUtils {

    // 获取页面编码
    private static final String CHARSET = "\"(?<=charset=)(.+)(?=\\\")\"";
    // 获取页面编码
    private static final Pattern PATTERN_CHARSET = Pattern.compile(CHARSET);

    /**
     * get webpage charset
     *
     * @param content content text
     * @return charset
     */
    public static String matchCharset(String content) {
        Matcher m = PATTERN_CHARSET.matcher(content);
        if (m.find()) {
            return m.group();
        }
        return null;
    }
}
