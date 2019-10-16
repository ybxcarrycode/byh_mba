package com.xszj.mba.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***************************************************************************
 * <pre></pre>
 *
 * @文件名称: FormatCheckUtils.java
 * @包 路 径： com.project.cloudwrite.utils
 * @类描述: 格式校验工具类, 校验手机号, 身份证号, OTP等是否符合要求
 * @版本: V1.5
 * @创建人： wangzhan
 * @创建时间：2015-7-25 上午2:09:26
 ***************************************************************************/

public class FormatCheckUtils {

    /**
     * 手机号校验函数
     * <p>
     * isMobileNum
     * </p>
     *
     * @param mobile 手机号
     * @return 校验结果
     * @Description:校验手机号是否合法
     */
    public static boolean isMobileNum(String mobile) {
        if (null == mobile) {
            return false;
        }

        if (mobile.length() == 0 || mobile.length() > 11) {
            return false;
        }

        Pattern p = Pattern
                .compile("^((1[3,4,5,7,8][0-9])|(15[^4,//D])|(170)|(177)|(18[0,5-9]))\\d{8}$");
        Matcher matcher = p.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 学号校验函数
     * <p>
     * isStuNumber
     * </p>
     *
     * @param number 学号
     * @return 校验结果
     * @Description:校验学号是否合法
     */
    public static boolean isStuNumber(String number) {

        if (number.length() == 0) {
            return false;
        }

        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * 密码校验函数
     * <p>
     * isPassword
     * </p>
     *
     * @param password 密码
     * @return 校验结果
     * @Description: 校验密码是否合法
     */
    public static boolean isPassword(String password) {

        if (password.length() == 0) {
            return false;
        }

        if (password.length() > 20 || password.length() < 6) {
            return false;
        }
//        for (int i = 0; i < password.length(); i++) {
//            char c = password.charAt(i);
//            if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
//                return false;
//            }
//        }
        String regPwd = "^[A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~\\;\\,\\/\\(\\)\\?\\'\\|\\\"\\:\\]\\[\\}\\{\\=\\-\\+\\_\\`\\~\\\\]{6,20}$";

        Pattern p = Pattern
                .compile(regPwd);
        Matcher matcher = p.matcher(password);
        return matcher.matches();

    }

    /**
     * 姓名校验函数
     * <p>isName</p>
     *
     * @param name
     * @return
     * @Description: 校验姓名是否为中文
     */
    public static boolean isName(String name) {

        if (name.length() == 0) {
            return false;
        }
//		char[] ch = name.toCharArray();
//		for (int i = 0; i < ch.length; i++) {
//			char c = ch[i];
//			if (!isChinese(c)) {
//				return false;
//			}
//		}

//		Pattern p = Pattern
//				.compile("^[\\u4e00-\\u9fa5]{2,12}$|^[\\dA-Za-z_]{3,18}$");
        Pattern p = Pattern
                .compile("^[\\u4E00-\\u9FA5A-Za-z0-9_-]{2,12}$");
        Matcher matcher = p.matcher(name);

        return matcher.matches();
    }


    /**
     * 邮箱校验函数
     * <p>isEmail</p>
     */
    public static boolean isEmail(String email) {

        if (null == email || "".equals(email) || email.length() == 0) {
            return false;
        }

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static boolean isString(String name) {

        if (name.length() == 0) {
            return false;
        }
        Pattern p = Pattern
                .compile("^[\\w\\u4e00-\\u9fa5\\-_][\\s\\w\\u4e00-\\u9fa5\\-_]*[\\w\\u4e00-\\u9fa5\\-_]{0,18}$");
        Matcher matcher = p.matcher(name);

        return matcher.matches();
    }


    private static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B

                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS

                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;

    }

}
