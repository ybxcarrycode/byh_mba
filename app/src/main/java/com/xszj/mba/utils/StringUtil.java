/*
 * @(#)StringUtil.java 11-5-16 下午7:56
 * CopyRight 2011. All rights reserved
 */
package com.xszj.mba.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Environment;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 封装对字符串的操作
 * 
 */
public class StringUtil {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public static boolean isNull(String str) {
		return str == null;
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() < 1;
	}

	public static boolean equals(String str1, String str2) {
		return str1 != null && str2 != null && str1.equals(str2);
	}

	public static boolean equals(Object object1, String str2) {
		if (object1 == null) {
			return false;
		} else {
			String str = object1.toString();
			return equals(str, str2);
		}
	}

	public static boolean isMobilephone(String mobiles) {
		if (mobiles.startsWith("86") || mobiles.startsWith("+86")) {
			mobiles = mobiles.substring(mobiles.indexOf("86") + 2);
		}
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 != null && str2 != null && str1.equalsIgnoreCase(str2);
	}

	public static boolean isContains(String str1, String str2) {
		return str1.contains(str2);
	}

	public static String getString(String str) {
		return str == null ? "" : str;
	}

	public static String unquote(String s, String quote) {
		if (!isNullOrEmpty(s) && !isNullOrEmpty(quote)) {
			if (s.startsWith(quote) && s.endsWith(quote)) {
				return s.substring(1, s.length() - quote.length());
			}
		}
		return s;
	}

	public static boolean equals(String contentType1, String contentType2, boolean ignoreCase) {
		if (contentType1 != null && contentType2 != null) {
			if (ignoreCase) {
				return contentType1.equalsIgnoreCase(contentType2);
			} else {
				return contentType1.equals(contentType2);
			}
		} else {
			return ((contentType1 == null && contentType2 == null) ? true : false);
		}
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + LINE_SEPARATOR);
				Log.d("socket read", sb.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 过滤HTML标签，取出文本内�?
	 * 
	 * @param htmlText
	 * @return
	 */
	public static String filterHtmlTag(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{�?script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{�?style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符�?
	}

	/**
	 * 将字符串数组转化为用逗号连接的字符串
	 * 
	 * @param values
	 * @return
	 */
	public static String arrayToString(String[] values) {
		String result = "";
		if (values != null) {
			if (values.length > 0) {
				for (String value : values) {
					result += value + ",";
				}
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}

	/**
	 * 验证字符串是否符合email格式
	 * 
	 * @param email
	 *            �?��验证的字符串
	 * @return 如果字符串为空或者为Null返回true�?
	 *         如果不为空或Null则验证其是否符合email格式，符合则返回true,不符合则返回false
	 */
	public static boolean isEmail(String email) {
		boolean flag = true;
		if (!isNullOrEmpty(email)) {
			// 通过正则表达式验证Emial是否合法
			flag = email.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@" + "([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

			return flag;
		}
		return flag;
	}

	/**
	 * 
	 * 时间转换为字符串
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date);
	}

	/**
	 * 替换字符串中特殊字符
	 * 
	 * @param strData
	 *            源字符串
	 * @return 替换了特殊字符后的字符串
	 */
	public static String encodeString(String strData) {
		if (strData == null) {
			return "";
		}
		strData = strData.replaceAll("&", "&amp;");
		strData = strData.replaceAll("<", "&lt;");
		strData = strData.replaceAll(">", "&gt;");
		strData = strData.replaceAll("'", "&apos;");
		strData = strData.replaceAll("\"", "&quot;");
		return strData;
	}

	public static String getMobileNumber(String str) {
		String result = str;
		if (!TextUtils.isEmpty(str)) {
			if (str.startsWith("+86")) {
				result = str.substring(3);
			}
			if (str.startsWith("86")) {
				result = str.substring(2);
			}
			Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(result);
			if (!m.matches()) {
				result = "";
			}
		}
		return result;
	}

	/**
	 * 还原字符串中特殊字符
	 * 
	 * @param strData
	 *            strData
	 * @return 还原后的字符串
	 */
	public static String decodeString(String strData) {
		if (strData == null) {
			return "";
		}
		strData = strData.replaceAll("&lt;", "<");
		strData = strData.replaceAll("&gt;", ">");
		strData = strData.replaceAll("&apos;", "'");
		strData = strData.replaceAll("&quot;", "\"");
		strData = strData.replaceAll("&amp;", "&");

		return strData;
	}

	/**
	 * 
	 * 按照一个汉字两个字节的方法计算字数
	 * 
	 * @param string
	 * @return
	 */
	public static int count2BytesChar(String string) {
		int count = 0;
		if (string != null) {
			for (char c : string.toCharArray()) {
				count++;
				if (isChinese(c)) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * 判断参数c是否为中文<BR>
	 * [功能详细描述] [added by 杨凡]
	 * 
	 * @param c
	 *            char
	 * @return 是中文字符返回true，反之false
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;

	}

	/**
	 * @category 将字符串转成 CharSequence
	 * */
	public static CharSequence getCharSequenceWithSmileyFromText(CharSequence text, Context context, float fontSize) {
		SpannableStringBuilder buffer = new SpannableStringBuilder();
		buffer.append(parseToSmiley(text, context, fontSize));
		return buffer;
	}

	public static CharSequence parseToSmiley(CharSequence text, Context context, float fontSize) {
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		String reg = "\\[\\S{1,3}\\]";
		Matcher matcher = Pattern.compile(reg).matcher(text);
		while (matcher.find()) {
			// Bitmap bitmap = FJBKApplication.getSmileyImg(matcher.group());
			// if(null !=bitmap){
			// int size = getFontHeight(fontSize);
			// size = dip2px(context,size);
			// ImageSpan img = new ImageSpan(Bitmap.createScaledBitmap(bitmap,
			// size, size, false),ImageSpan.ALIGN_BOTTOM);
			// builder.setSpan(img, matcher.start(),
			// matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			// }

		}
		return builder;
	}

	public static int getFontHeight(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.ascent) + 10;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 插入表情
	 * */
	public static void insertSmileyToEditText(Context context, String insertText, EditText etText) {
		int selectIndex = etText.getSelectionStart();
		Editable wholeText = etText.getText();
		SpannableStringBuilder buffer = new SpannableStringBuilder();
		CharSequence startText = wholeText.subSequence(0, selectIndex);
		CharSequence endText = wholeText.subSequence(selectIndex, wholeText.length());
		CharSequence add = getCharSequenceWithSmileyFromText(insertText, context, etText.getPaint().measureText("哈"));

		buffer.append(startText);
		buffer.append(add);
		// 记录当前焦点坐标
		int cuurentFocus = buffer.length();
		buffer.append(getCharSequenceWithSmileyFromText(endText, context, etText.getPaint().measureText("哈")));
		etText.setText(buffer);
		Editable etext = etText.getText();
		Selection.setSelection(etext, Math.min(cuurentFocus, etext.length()));
	}

	/**
	 * 插入at到编辑框中
	 * */
	public static void insertAtToEditText(String insertText, EditText etText) {
		int selectIndex = etText.getSelectionStart();
		Editable wholeText = etText.getText();
		SpannableStringBuilder buffer = new SpannableStringBuilder();
		CharSequence startText = wholeText.subSequence(0, selectIndex);
		CharSequence endText = wholeText.subSequence(selectIndex, wholeText.length());
		CharSequence add = insertText;
		buffer.append(startText);
		buffer.append("@" + add + " ");
		// 记录当前焦点坐标
		int cuurentFocus = buffer.length();
		buffer.append(endText);
		etText.setText(buffer);
		Editable etext = etText.getText();
		Selection.setSelection(etext, Math.min(cuurentFocus, etext.length()));
	}

	public static String getJSONStringFromCursor(Cursor cursor) {
		if (null != cursor && 0 < cursor.getCount()) {
			return cursor.getString(cursor.getColumnIndex("json"));
		}
		return null;
	}

	public static CharSequence atToString(String content, Context context, float fontSize) {
		String reg = "<at\\s+uid=\"(\\d+)\"\\s?>([^<>]*?)</at>";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			content = content.replace(matcher.group(0), matcher.group(2));
		}
		return parseToSmiley(content, context, fontSize);
	}

	public static String getTime(int seconds) {
		String time = "";
		int min = seconds / 60;
		int sen = seconds % 60;
		String strMin = 10 > min ? "0" + min : "" + min;
		String strSec = 10 > sen ? "0" + sen : "" + sen;
		time = strMin + ":" + strSec;
		return time;
	}

	public static String getSDCardPath() {
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息
				if (lineStr.toLowerCase().contains("sdcard") && lineStr.contains(".android_secure")) {
					String[] strArray = lineStr.split(" ");
					if (strArray != null && strArray.length >= 5) {
						String result = strArray[1].replace("/.android_secure", "");
						if (!result.equals(Environment.getExternalStorageDirectory().getPath()))
							return result;
					}
				}
				// 检查命令是否执行失败。
				if (p.waitFor() != 0 && p.exitValue() == 1) {
					// p.exitValue()==0表示正常结束，1：非正常结束
				}
			}
			inBr.close();
			in.close();
		} catch (Exception e) {

			return Environment.getExternalStorageDirectory().getPath();
		}

		return Environment.getExternalStorageDirectory().getPath();
	}

	public static String getCountStr(int count) {
		String str = "";

		int thoundsand = 1000;
		int tenThoundsand = 10 * thoundsand;
		int hundredThoundsand = 10 * tenThoundsand;
		int million = 10 * hundredThoundsand;
		int tenMillion = 10 * million;
		int hundredMillion = 10 * tenMillion;
		int left = count;
		if (count < tenThoundsand) {
			return "" + count;
		}
		if (hundredMillion <= left) {
			int b = left / hundredMillion;
			left = changeToTenThoundsand(left);
			str += b + "亿";
		}
		if (tenThoundsand <= left) {
			int b = left / tenThoundsand;
			str += b + "万";
		}
		return str + "+";
	}

	private static int changeToTenThoundsand(int count) {
		int result = count;
		while (10000 * 10000 <= result) {
			result %= 10;
		}
		return result;
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		// 16位加密，从第9位到25位
		return md5StrBuff.substring(8, 24).toString().toUpperCase();
	}

	public static String getShortX(String string, int x) {
		//		if (x <= string.length()) {
		//			if (x > 3) {
		//				return string.substring(0, x-3) + "...";
		//			} else {
		//				return string.substring(0, x) + "...";
		//			}
		//		}
		return string;
	}

	public static String getShortX(String string, int x, int less) {
		//		if (x <= string.length()) {
		//			if (x > 3) {
		//				return string.substring(0, x-less) + "...";
		//			} else {
		//				return string.substring(0, x) + "...";
		//			}
		//		}
		return string;
	}

	public static boolean notEmpty(Object o) {
		return o != null && !"".equals(o.toString().trim()) && !"null".equalsIgnoreCase(o.toString().trim()) && !"undefined".equalsIgnoreCase(o.toString().trim());
	}


	/**
	 * 
	 * 功能:显示roleTag
	 * @author yinxuejian
	 * @param roleTag1
	 * @param roleTag2
	 * @return
	 */
	public static String showExpertRoleTagMsg(String roleTag1, String roleTag2) {
		String msg = "";
		if (StringUtil.notEmpty(roleTag1) && StringUtil.notEmpty(roleTag2)) {
			msg = roleTag1 + " | " + roleTag2;
		} else if (!StringUtil.notEmpty(roleTag1) && StringUtil.notEmpty(roleTag2)) {
			msg = roleTag2;
		} else if (StringUtil.notEmpty(roleTag1) && !StringUtil.notEmpty(roleTag2)) {
			msg = roleTag1;
		}
		return msg;
	}

	/**
	 * 
	 * 功能:是否是达人
	 * @author yinxuejian
	 * @param userType
	 * @return
	 */
	public static boolean isExpert(String userType) {
		if (userType.equals("11") || userType.equals("14") || userType.equals("12") || userType.equals("13")) {
			return false;
		}
		return true;
	}

	public static String getUserTypeName(String typeId) {
		String name = "";
		if (typeId.equals("13")) {
			name = "学长学姐";
		} else if (typeId.equals("14")) {
			name = "招办老师";
		} else if (typeId.equals("15")) {
			name = "备考专家";
		} else {
			name = "普通学员";
		}
		return name;
	}

	public static String selectHobbiesTypeName(String typeId) {
		String name = "";
		if (typeId.equals("101")) {
			name = "联考答疑";
		} else if (typeId.equals("102")) {
			name = "经验分享";
		} else if (typeId.equals("103")) {
			name = "院校信息";
		} else if (typeId.equals("104")) {
			name = "申请材料";
		}else if (typeId.equals("105")) {
			name = "其他";
		}else if (typeId.equals("106")) {
			name = "面试攻略";
		}
		return name;
	}

	/** 
	   * 获取application中指定的meta-data 
	   * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空 
	   */
	public static String getAppMetaData(Context ctx, String key) {
		if (ctx == null || TextUtils.isEmpty(key)) {
			return null;
		}
		String resultData = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						resultData = applicationInfo.metaData.getString(key);
					}
				}

			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return resultData;
	}

}
