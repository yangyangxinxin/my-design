package com.zyl.service.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成文件MD5
 * @author Alien
 *
 */
public class Md5Maker {
	/**
	 * Md5 加密算法
	 * @param plainText 需要加密的字符串
	 * @param buf_md5 加密方式 1:32位 2 62位  默认为32位加密
	 * @return -1 加密错误
	 */
	public static String buf_MD5(String plainText,String buf_md5) { 
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes()); 
			byte b[] = md.digest(); 
			int i; 
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
				i = b[offset]; 
				if(i<0) i+= 256; if(i<16) 
				buf.append("0"); buf.append(Integer.toHexString(i)); 
			} 
			if("2".equals(buf_md5)){
				//64位的加密 
				return buf.toString().substring(8,24);
			}else{
				//32位的加密 
				return buf.toString();
			}
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
			return "-1";
		} 
	}

	public static String bytesToString(byte[] data) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        char[] temp = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            temp[i * 2] = hexDigits[b >>> 4 & 0x0f];
            temp[i * 2 + 1] = hexDigits[b & 0x0f];
        }
        return new String(temp);
    }
	
	public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            return bytesToString(md.digest());
        } catch (Exception ex) {
            return null;
        } finally {
            try {
                fis.close();
            } catch (Exception ex) {
            }
        }
    }
	
	
}
