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

	public static String firstUpper(String str) {
		StringBuffer stringbf = new StringBuffer();
		Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(str);

		while (m.find()) {
			return m.group(1).toUpperCase() + m.group(2).toLowerCase();
//			m.appendReplacement(stringbf, m.group(1).toUpperCase() + m.group(2).toLowerCase());
		}
		return m.appendTail(stringbf).toString();
	}

	static class Test {
		public static void main(String[] args) {
			System.out.println(System.currentTimeMillis());
		}
	}
}


