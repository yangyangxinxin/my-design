package com.zyl.util;

import java.util.Random;

import com.zyl.exception.LengthExc;

/**
 * 验证码,随机数工厂
 * 
 * @author lenovo
 *
 */
public class CodeFactory {
	
	private final static int[] CODE_NUM={0,1,2,3,4,5,6,7,8,9};
	
	private final static char[] CODE_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z' };
	
	/**
	 * 获得长度为length的随机数,length不低于3位,并且不得高于9位
	 * @param length
	 * @return int
	 */
	public static int getRandomNum(int length) throws LengthExc{
		if(!judgeLenth(3,9,length)){
			throw new LengthExc("erro_code:2000,请输入正确的长度");
		}
		Random rd= new Random();
		String num="";
		num+=CODE_NUM[rd.nextInt(9)+1];
		for(int i=1;i<length;i++){
			num+=CODE_NUM[rd.nextInt(10)];
		}
		return Integer.parseInt(num);
	}
	
	/**
	 * 获取长度为length为随机字母与数字,length不低于3位,并且不得高于9位
	 * @param length
	 * @return
	 * @throws LengthExc
	 */
	public static String getRandomCode(int length) throws LengthExc{
		if(!judgeLenth(3,9,length)){
			throw new LengthExc("erro_code:2000,请输入正确的长度");
		}
		Random rd= new Random();
		String code="";
		for(int i=0;i<length;i++){
			code+=CODE_CHAR[rd.nextInt(CODE_CHAR.length)];
		}
		return code;
	}
	
	/**
	 * 长度验证
	 * @param minLength 最低长度
	 * @param maxLength	最高长度
	 * @param length	验证长度长度
	 * @return
	 */
	public static boolean judgeLenth(Integer minLength,Integer maxLength,int length){
		if(minLength==null&&maxLength!=null){
			if(length>maxLength){
				return false;
			}
			return true;
		}
		if(minLength!=null&&maxLength==null){
			if(length<minLength){
				return false;
			}
			return true;
		}
		if(minLength!=null&&maxLength!=null){
			if(length>maxLength||length<minLength){
				return false;
			}
			return true;
		}
		return false;
	}

}
