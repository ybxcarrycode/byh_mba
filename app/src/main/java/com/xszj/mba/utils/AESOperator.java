package com.xszj.mba.utils;


import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AESOperator {

    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private String sKey = "yunshuxie.com129";//key，可自行修改
    private String ivParameter = "0507088888881029";//偏移量,可自行修改
    private static AESOperator instance = null;

    private AESOperator() {

    }

    public static AESOperator getInstance() {
        if (instance == null)
            instance = new AESOperator();
        return instance;
    }

public static String Encrypt(String encData ,String secretKey,String vector) throws Exception {

        if(secretKey == null) {
            return null;
        }
        if(secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return StringUtils.base64Encode(encrypted);// 此处使用BASE64做转码。
    }


    // 加密
    public String encrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return StringUtils.base64Encode(encrypted);// 此处使用BASE64做转码。
    }

   /* // 解密
    public String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    public String decrypt(String sSrc,String key,String ivs) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }*/

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }
    /**
    * 讲一个字符串使用AES加密
     */
    public static String enc(String str) throws Exception {
        return  AESOperator.getInstance().encrypt(str);
    }
    /**
    * 讲一个map使用AES加密
     */
    public static String enc(Map<String,String> map){
        String aesStr = "";
        try {
            Gson g = new Gson();
            String json =  g.toJson(map);
            aesStr = AESOperator.getInstance().encrypt(json.toString());
            return aesStr;
        } catch (Exception e) {
            e.printStackTrace();
            return aesStr;
        }
    }

   /* public static String dec(String str)throws Exception {
        return  AESOperator.getInstance().decrypt(str);
    }*/
    public static void main(String[] args) throws Exception {
        // 需要加密的字串
        String cSrc = "测试";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memebrId","测试数据");
        map.put("id","1000");
        Gson g = new Gson();
        String json =  g.toJson(map);
        // 加密
        String enString = AESOperator.getInstance().enc(cSrc);
        System.out.println("加密后的字串是：" + enString);

       /* // 解密
        String DeString = AESOperator.getInstance().decrypt(enString);
        System.out.println("解密后的字串是：" + DeString);
        System.out.println("解密后的字串是：" + String.valueOf(1));*/

    }

}
