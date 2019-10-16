package com.xszj.mba.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String encryptToMd5(String plainText) {
		
		return bytesToMD5(plainText.getBytes());
		
    }
	
   public static String bytesToMD5(byte[] bytes){
	   MessageDigest md = null;
	   byte[] b = null;
	   StringBuffer buf = null;
	   int i = 0;
	   try {
			md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			b = md.digest();
			buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	   
   }
   
   public static String fileToMD5(String filename) {
	   
       InputStream fis;

       byte[] buffer = new byte[1024];
       
       byte[] b = null;

       int numRead = 0;

       MessageDigest md5;
       
       int i = 0;
       
       StringBuffer buf = null;

       try{

           fis = new FileInputStream(filename);

           md5 = MessageDigest.getInstance("MD5");

           while((numRead=fis.read(buffer)) > 0) {

               md5.update(buffer,0,numRead);

           }

           fis.close();
           
           b = md5.digest();
		   buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();

       } catch (Exception e) {
    	   e.printStackTrace();
           return "";

       }

   }

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
	
