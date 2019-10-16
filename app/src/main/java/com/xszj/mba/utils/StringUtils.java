package com.xszj.mba.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.apache.commons.codec.binary.Base64;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/***************************************************************************
 * <pre></pre>
 *
 * @文件名称: StringUtils.java
 * @包 路   径：  com.project.cloudwrite.utils
 * @类描述: 字符串工具类, 判空, 判断是否为中文, Base64编码与解码, JSON格式转换等;
 * @版本: V1.5
 * @创建人： wangzhan
 * @创建时间：2015-7-25 上午2:07:55
 ***************************************************************************/

@SuppressLint("NewApi")
public class StringUtils {

    /**
     * 空串判断
     * <p>isEmpty</p>
     *
     * @param string
     * @return
     * @Description:判断是否为空字符串
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 中文判断
     * <p>isChinese</p>
     *
     * @param string
     * @return
     * @Description:判断是否为中文
     */
    public static boolean isChinese(String string) {
        char[] ch = string.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChinese(c)) {
                return false;
            }
        }

        return true;
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

    /**
     * Base64 编码
     * <p>base64Encode</p>
     *
     * @param data 输入二进制数组
     * @return 编码后Base64字符串
     * @Description:对二进制数组做Base64编码
     */
    public static String base64Encode(byte[] data) {
        return new String(Base64.encodeBase64(data)).toString();
    }

//    /**
//     * Base64解码
//     * <p>base64Decode</p>
//     *
//     * @param base64 待解码Base64字符串
//     * @return 解码后二进制数组
//     * @Description: 对Base64字符串做解码处理
//     */
//    public static byte[] base64Decode(String base64) {
//        if (Base64.isArrayByteBase64(base64.getBytes()) == false) {
//            return "".getBytes();
//        }
//        return Base64.decodeBase64(base64.getBytes());
//    }
    public static String getHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
        } catch (Exception e) {
        }
        return null;
    }

    public static String getLocalIpAddress(Context context) {
        if (isNetworkConnected(context)) {
            try {
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int i = wifiInfo.getIpAddress();
                return int2ip(i);
            } catch (Exception ex) {
                return "192.168.0.1";
            }
        }
        return "192.168.0.1";
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    //判断网络是否连接
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


}
