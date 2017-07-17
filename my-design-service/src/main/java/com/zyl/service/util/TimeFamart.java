package com.zyl.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFamart {
	public static String getTime( String pattern){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date.getTime());
	}
	public static Date getDate(String time,String pattern){
		Date date=null;  
		//"yyyy-MM-dd HH:mm:ss"
	    SimpleDateFormat formatter=new SimpleDateFormat(pattern);  
	    try {
			date=formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return date;
	}
}
