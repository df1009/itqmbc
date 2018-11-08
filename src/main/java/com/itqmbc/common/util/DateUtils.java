package com.itqmbc.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils
{
  public static Date getSystemDate()
  {
    return new Date();
  }

  public static Date getDate()
  {
    Calendar calendar = getSystemCalendar();
    calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
    return convertString2Date(convertDate2String(calendar.getTime(), "yyyyMMdd"), "yyyyMMdd");
  }

  public static Date getDateTime()
  {
    return getSystemDate();
  }

  public static Calendar getSystemCalendar()
  {
    return Calendar.getInstance();
  }

  public static String getTimeStamp()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return sdf.format(getSystemDate());
  }

  public static Date convertString2Date(String str, String pattern)
  {
    if ((str == null) || ("".equals(str)))
      return null;

    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(sdf.parse(str.trim()));
    } catch (ParseException ex) {
      throw new RuntimeException(ex);
    }
    return calendar.getTime();
  }

  public static String convertDate2String(Date date, String pattern)
  {
    if (date == null)
      return null;

    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(date);
  }

  public static int getYear(Date date)
  {
    if (date == null)
      return 0;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(1);
  }

  public static int getMonth(Date date)
  {
    if (date == null)
      return 0;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return (calendar.get(2) + 1);
  }

  public static int getDay(Date date)
  {
    if (date == null)
      return 0;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(5);
  }

  public static int getHour(Date date)
  {
    if (date == null)
      return 0;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(11);
  }

  public static int getMinute(Date date)
  {
    if (date == null)
      return 0;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(12);
  }

  public static int getSecond(Date date)
  {
    if (date == null)
      return 0;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(13);
  }

  public static Date addYear(Date date, int year)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(1, year);
    return calendar.getTime();
  }

  public static Date addMonth(Date date, int month)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(2, month);
    return calendar.getTime();
  }

  public static Date addDay(Date date, int day)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, day);
    return calendar.getTime();
  }

  public static Date addHour(Date date, int hour)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(11, hour);
    return calendar.getTime();
  }

  public static Date addMinute(Date date, int min)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(12, min);
    return calendar.getTime();
  }

  public static Date addSecond(Date date, int second)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(13, second);
    return calendar.getTime();
  }

  public static Date getFirstDay(Date date)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(5, 1);
    return calendar.getTime();
  }

  public static Date getLastDay(Date date)
  {
    if (date == null)
      return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(5, 1);
    calendar.roll(5, -1);

    return calendar.getTime();
  }

  public static boolean isValidDate(String date, String pattern)
  {
    if (date == null)
      return false;

    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      sdf.setLenient(false);
      sdf.parse(String.valueOf(date));
    } catch (ParseException ex) {
      return false;
    }
    return true;
  }

  public static int dateDiff(Date stateDate, Date endDate)
  {
    return (int)((endDate.getTime() - stateDate.getTime()) / 86400000L);
  }

  public static boolean isLeapYear(int year)
  {
    return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
  }

  public static int getDaysIn2Day(String dateStrFrom, String dateStrTo)
  {
    if ((StringUtil.isEmpty(dateStrFrom)) || (StringUtil.isEmpty(dateStrTo))) {
      return 0;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar fromCalendar = Calendar.getInstance();
    Calendar toCalendar = Calendar.getInstance();
    try {
      fromCalendar.setTime(sdf.parse(dateStrFrom.trim()));
      toCalendar.setTime(sdf.parse(dateStrTo.trim()));
    } catch (ParseException ex) {
      throw new RuntimeException(ex);
    }
    long day = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / 86400000L;

    return ConvUtils.convToInt(Long.valueOf(day));
  }

  public static int getMonthNum(Date dateStrFrom, Date dateStrTo)
  {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(dateStrFrom);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(dateStrTo);
    return ((cal2.get(1) - cal1.get(1)) * 12 + cal2.get(2) - cal1.get(2));
  }

  public static String formatDateTime(Object origDate, String origPattern, String destPattern)
  {
    String formatDate = "";
    String strDate = "";
    if (origDate == null)
      return formatDate;

    strDate = String.valueOf(origDate).trim();
    if (("".equals(strDate)) || ("0".equals(strDate))) {
      return formatDate;
    }

    SimpleDateFormat sdf = new SimpleDateFormat(origPattern);
    Calendar calendar = Calendar.getInstance();
    try {
      calendar.setTime(sdf.parse(strDate));
      sdf.applyPattern(destPattern);
      formatDate = sdf.format(calendar.getTime());
    } catch (ParseException ex) {
      throw new RuntimeException(ex);
    }

    return formatDate;
  }
}