package org.demon.utils.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作类
 */
public class StringUtils {
	private StringUtils() {}

	/**
	 * 判断字符串是否为数字的方法
	 */
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断一个字符串是否都为数字
	 */
	public static boolean isDigit(String strNum) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher((CharSequence) strNum);
		return matcher.matches();
	}

	public static String uncapitalize(String str) {
		return org.apache.commons.lang.StringUtils.uncapitalize(str);
	}
}


