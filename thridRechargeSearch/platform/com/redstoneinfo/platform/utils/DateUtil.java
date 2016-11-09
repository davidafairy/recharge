/**
 * @{#} DateUtil.java Created on 2006-10-18 ����10:35:08
 *
 * Copyright (c) 2006 by WASU.
 */
package com.redstoneinfo.platform.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * ���ڹ�����
 * 
 * @author <a href="mailto:chengj@onewaveinc.com">wilson</a>
 * @version 1.0
 */
public class DateUtil {
	/**
	 * ����һ��ĺ�����
	 */
	public static final long MILLSECOND_OF_DAY = 86400000;

	/**
	 * ��ʽ������
	 * 
	 * @param strDate
	 *            ��ϸ�ʽ���ַ�
	 * @return ��ʽ�������
	 */
	public static Date parser(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	} 
	
	/**
	 * ��ʽ������
	 * 
	 * @param strDate
	 *            ��ϸ�ʽ���ַ�
	 * @return ��ʽ�������
	 */
	public static Date parserShort(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}	
	
	/**
	 * ��ʽ������
	 * 
	 * @param strDate
	 *            ��ϸ�ʽ���ַ�
	 * @return ��ʽ�������
	 */
	public static Date parserByType(String strDate,int parseType) {
		SimpleDateFormat sdf = null ;
		if(parseType == 1){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else if(parseType == 2){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else if(parseType == 3){
			sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		}else if(parseType == 4){
			sdf = new SimpleDateFormat("yyyyMMdd");
		}else{
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��ʽ������
	 * 
	 * @param strDate
	 *            ��ϸ�ʽ���ַ�
	 * @return ��ʽ�������
	 */
	public static Date parserDate(Date testDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(testDate);
		calendar.clear(Calendar.MILLISECOND);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.HOUR_OF_DAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(calendar.getTime()));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��ʽ������
	 * 
	 * @param strDate
	 *            ��ϸ�ʽ���ַ�
	 * @return ��ʽ�������
	 */
	public static Date parserTo(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * �õ���ǰ�·ݵ����ڿ�ʼ����
	 * 
	 * @param currentDate
	 *            ��ǰ����
	 * @return ��ǰ�·ݵ����ڿ�ʼ����
	 */
	public static Date getCurBeginCycleDate(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		String year = "" + calendar.get(Calendar.YEAR);
		String month = (calendar.get(Calendar.MONTH) + 1) + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		String dateStr = year + "-" + month + "-01 00:00:00";
		return DateUtil.parser(dateStr);
	}

	/**
	 * ȡ�õ�ǰ���ڵ����һ�� yyyy-MM-dd 23:59:59
	 * 
	 * @param currentDate
	 * @return
	 */
	// addby zhouxh 20071214
	public static Date getCurrEnd(Date currentDate) {
		currentDate.setHours(23);
		currentDate.setMinutes(59);
		currentDate.setSeconds(59);
		return currentDate;
	}

	/**
	 * ȡ�õ�ǰ���ڵ����ڽ�������
	 * 
	 * @param currentDate
	 *            ��ǰ����
	 * @return ��ǰ���ڵ����ڽ�������
	 */
	public static Date getCurEndCycleDate(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		String year = "" + calendar.get(Calendar.YEAR);
		String month = (calendar.get(Calendar.MONTH) + 2) + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		String dateStr = year + "-" + month + "-01 23:59:59";
		calendar.setTime(DateUtil.parser(dateStr));
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * �õ���nextCycle���ڵ��·�
	 * 
	 * @param currentDate
	 *            ��ǰ����
	 * @param nextCycle
	 *            ��nextCycle����
	 * @return ��nextCycle����
	 */
	public static Date getNextCycleDate(Date currentDate, long nextCycle) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		String year = "" + calendar.get(Calendar.YEAR);
		nextCycle++;
		String month = (calendar.get(Calendar.MONTH) + nextCycle) + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		String dateStr = year + "-" + month + "-01 00:00:00";
		return DateUtil.parser(dateStr);
	}

	/**
	 * ��ȡ��ʼ�ͽ�������֮��ļ������
	 * 
	 * @param startDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��������
	 * @param roundingMode
	 *            ���뷽ʽ �� BigDecimal�Ķ���
	 * @return �����������
	 */
	public static long getDaysBetweenDate(Date startDate, Date endDate, int roundingMode) {

		BigDecimal bStart = new BigDecimal(startDate.getTime());
		BigDecimal bEnd = new BigDecimal(endDate.getTime());
		BigDecimal bUnit = new BigDecimal(MILLSECOND_OF_DAY);
		return (bEnd.subtract(bStart)).divide(bUnit, roundingMode).longValue();
	}

	/**
	 * ��ȡ��ʼ�ͽ�������֮��ļ������
	 * 
	 * @param startDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��������
	 * @return �����������
	 */
	public static long getDaysBetweenDateWithoutTime(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal2.setTime(endDate);

		cal1.clear(Calendar.MILLISECOND);
		cal1.clear(Calendar.SECOND);
		cal1.clear(Calendar.MINUTE);
		cal1.clear(Calendar.HOUR_OF_DAY);

		cal2.clear(Calendar.MILLISECOND);
		cal2.clear(Calendar.SECOND);
		cal2.clear(Calendar.MINUTE);
		cal2.clear(Calendar.HOUR_OF_DAY);

		return (cal2.getTime().getTime() - cal1.getTime().getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTomorrowDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * ��ȡ��������֮�������·���
	 * 
	 * @param startDate
	 *            ��ʼ����
	 * @param endDate
	 *            ��������
	 * @param flag
	 *            false Ϊȫ����
	 * @return ���ص��·���
	 */
	public static long getMonthsBetweenDate(Date startDate, Date endDate, boolean flag) {
		Calendar cal1 = Calendar.getInstance();

		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		if (endDate.before(startDate)) {
			cal1.setTime(endDate);
			cal2.setTime(startDate);
		}

		cal1.clear(Calendar.MILLISECOND);
		cal1.clear(Calendar.SECOND);
		cal1.clear(Calendar.MINUTE);
		cal1.clear(Calendar.HOUR_OF_DAY);

		cal2.clear(Calendar.MILLISECOND);
		cal2.clear(Calendar.SECOND);
		cal2.clear(Calendar.MINUTE);
		cal2.clear(Calendar.HOUR_OF_DAY);

		return getMonthsBetweenDate(cal1, cal2, flag);

	}

	/**
	 * ��ȡ��������֮�������·���
	 * 
	 * @param cal1
	 *            ��ʼ����
	 * @param cal2
	 *            ��������
	 * @param flag
	 *            false Ϊȫ����
	 * @return ���ص��·���
	 */
	public static long getMonthsBetweenDate(Calendar cal1, Calendar cal2, boolean flag) {
		long month = 0L;
		while (cal1.before(cal2)) {
			cal1.add(Calendar.MONTH, 1);
			month++;
			if (flag) {
				if ((cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
						&& (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
						&& (cal1.get(Calendar.DAY_OF_MONTH) > cal2.get(Calendar.DAY_OF_MONTH))) {
					month--;
					break;
				}
				if ((cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH))
						&& (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))

				) {
					month--;
					break;
				}
			}
		}
		return month;
	}

	/**
	 * 
	 * @param date
	 * @param field
	 * @return
	 */
	public static long getDateField(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (field == Calendar.MONTH)
			return cal.get(field) + 1;
		else
			return cal.get(field);

	}

	/**
	 * 
	 * @param date
	 * @param field
	 * @return
	 */
	public static Date getNextDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();

	}

	public static Date getAfterDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static Date getAfterSecond(Date date, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
        return cal.getTime();
	}
	
	/**
	 * ��ǰ������ date  months����
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getAfterMonth(Date date,int months){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * ����ʱ���
	 * 
	 * @param endDate
	 * @param beginDate
	 * @return
	 */
	public static int diffDate(Date endDate, Date beginDate) {
		return (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
	}

	public static void main22(String[] args) {
		/*
		 * Calendar cal1 = Calendar.getInstance(); Calendar cal2 =
		 * Calendar.getInstance(); cal1.set(2006, 11, 13, 10, 10);
		 * cal2.set(2006, 6, 18, 10, 30); //
		 * System.out.println(DateUtil.getMonthsBetweenDate(cal1,cal2,false));
		 * System.out.println(DateUtil.parser(cal1.getTime()));
		 * /*System.out.println(DateUtil.getDateField(new Date(),
		 * Calendar.YEAR)); System.out.println(DateUtil.getDateField(new Date(),
		 * Calendar.DATE)); System.out.println(DateUtil.getDateField(new Date(),
		 * Calendar.MONTH));
		 */
		// System.out.println(DateUtil.getTomorrowDate(new Date()));
		Date date = DateUtil.getNextDate(new Date());
		System.out.println(date);
		Date date2 = DateUtil.getNextDate(date);
		System.out.println(date2);

	}

    public static  String getFormatedDateStr(Date date,int formatType) {
        	SimpleDateFormat sdf = null ;
        	if(formatType == 1){
        		sdf = new SimpleDateFormat("yyyyMMdd");
        	}else if(formatType == 2){
        		sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	}else if(formatType == 3){
        		sdf =  new SimpleDateFormat("yyyy-MM-dd");
        	}else if(formatType == 4){
        		sdf =  new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        	}else if(formatType == 5){
        		sdf =  new SimpleDateFormat("yyyyMMddHHmm");
        	}else{
        		sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	} 
            return sdf.format(date);
    }
    

	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat("yyyyMMdd");
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	 
	public static void main(String[] args) {
		
		System.out.println(getFormatedDateStr(new Date(),3));

	}

	public static String format(Date ldate, String pattern) {
		try{
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(ldate);	
		}catch(Exception e){
			return null;
		}
		
	}
	public static Date getDurationDate(Date date, Integer duration) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.DATE, duration);

		lastDate.set(Calendar.HOUR, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		lastDate.set(Calendar.MILLISECOND,0);
		return lastDate.getTime();
	}
	public static String getDurationDateStr(Date date, Integer duration) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.DATE, duration);
		str = sdf.format(lastDate.getTime());
		return str;
	}
	public static Date parserDate(String strDate,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}
	public static String getDurationDateMin(Date date, Integer duration) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.MINUTE, duration);
		str = sdf.format(lastDate.getTime());
		return str;
	}
	public static String getbeforeDate(Date date) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DATE, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}
	public static String getbeforeDate(Date date,Integer befroeduration) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DATE, -befroeduration);
		str = sdf.format(lastDate.getTime());
		return str;
	}
}
