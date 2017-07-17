package com.zyl.exception;

/**
 * 自定义异常类
 * 长度异常
 * @author lenovo
 *
 */
public class LengthExc extends Exception{

	private static final long serialVersionUID = -9053605414447690363L;
	
	public LengthExc() {
	}
	public LengthExc(String message) {
		super(message);
	}
	
	
}
