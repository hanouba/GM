package com.yoursecondworld.secondworld.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * 汉字转拼音工具类<br>
 * 基于 pinyin4j
 *
 * @author Jayden
 */
public class PinyinUtil {

	private static PinyinUtil instance;

	public static PinyinUtil getInstance() {
		if (instance == null) {
			instance = new PinyinUtil();
		}
		return instance;
	}

	private HanyuPinyinOutputFormat outputFormat = null;

	private HanyuPinyinOutputFormat getOutputFormat() {
		if (outputFormat == null) {
			outputFormat = new HanyuPinyinOutputFormat();
			outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		}
		return outputFormat;
	}

	/**
	 * 返回字符串汉子首个汉子的首字母
	 *
	 * @param content
	 * @return
	 */
	public char getOneStringInitial(String content) {
		try {
			if (content.trim().length() > 0) {
				return getCharInitial(content.charAt(0));
			} else {
				return '#';
			}
		} catch (Exception e) {
			return '#';
		}
	}

	/**
	 * 获取字符串中所有字符首字母
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String getStringInitial(String str) {
		StringBuffer sbf = new StringBuffer();
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				sbf.append(getCharInitial(str.charAt(i)));
			}
		}
		return sbf.toString();
	}

	/**
	 * 获取中文字符首字母
	 *
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public char getCharInitial(char c) {

		if (c >= 65 && c <= 90) { // 说明是大写字母
			return c;
		}

		if (c >= 97 && c <= 122) { // 说明是小写字母
			return (char) (c - 32);
		}

		try {
			String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, getOutputFormat());
			char initial = '#';
			if (strs != null && strs.length > 0) {
				String str = strs[0];
				if (str != null && str.length() > 0) {
					initial = str.charAt(0);
				}
			}

			if (initial >= 97 && initial <= 122) { // 说明是小写字母
				initial =  (char) (initial - 32);
			}
			return initial;
		} catch (Exception ex) {
			return '#';
		}
	}

	public String getStringPinyin(String chines) {

		char[] nameChar = chines.toCharArray();
		String pinyinStr = "";
		for (int i = 0; i < nameChar.length; i++) {
			try {
				char cha = nameChar[i];
				if (nameChar[i] > 128) {
					pinyinStr += PinyinHelper.toHanyuPinyinStringArray(cha, getOutputFormat())[0];
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				pinyinStr += nameChar[i];
			}
		}
		return pinyinStr;
	}

	/**
	 * 获取字符全拼
	 *
	 * @param c
	 * @return
	 */
	public String getCharPinyin(char c) {

		try {
			String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, getOutputFormat());
			String str = "";
			if (strs != null && strs.length > 0) {
				str = strs[0];
			}
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			return c + "";
		}
	}
}
