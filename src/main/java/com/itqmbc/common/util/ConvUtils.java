package com.itqmbc.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConvUtils
{
  public static String convToString(Object obj)
  {
    if (obj == null)
      return "";

    if (StringUtil.isEmpty(obj.toString()))
      return "";

    return obj.toString();
  }

  public static boolean convToBool(Object obj)
  {
    if (obj == null)
      return false;

    if (obj instanceof byte[])
      return convBitToBool((byte[])obj);

    if ("1".equals(obj.toString()))
      return true;
    if ("0".equals(obj.toString()))
      return false;
    if ("true".equalsIgnoreCase(obj.toString()))
      return true;

    return (!("false".equalsIgnoreCase(obj.toString())));
  }

  public static boolean convBitToBool(byte[] bytes)
  {
    if (bytes == null)
      return false;

    return (bytes[0] == 1);
  }

  public static int convToInt(Object value)
  {
    int intValue = 0;
    if (value == null)
      return intValue;
    try
    {
      intValue = Integer.parseInt(value.toString().trim().replace(",", ""));
    } catch (NumberFormatException ex) {
      return intValue;
    }

    return intValue;
  }

  public static long convToLong(Object value)
  {
    long longValue = -3600855780162535424L;
    if (value == null)
      return longValue;
    try
    {
      longValue = Long.parseLong(value.toString().trim().replace(",", ""));
    } catch (NumberFormatException ex) {
      return longValue;
    }

    return longValue;
  }

  public static double convToDouble(Object pValue)
  {
    double dblValue = 0D;
    if (pValue == null)
      return dblValue;
    try
    {
      dblValue = Double.parseDouble(String.valueOf(pValue).trim().replace(",", ""));
    } catch (Exception ex) {
      return dblValue;
    }
    return dblValue;
  }

  public static String convToClearZero(String str) {
    DecimalFormat df2 = new DecimalFormat("#,##0.##");
    return df2.format(convToDouble(str));
  }

  public static BigDecimal convToDecimal(Object value)
  {
    BigDecimal dec = new BigDecimal("0");
    if (value == null)
      return dec;
    if (value instanceof BigDecimal)
      dec = (BigDecimal)value;
    else
      try {
        dec = new BigDecimal(String.valueOf(value).trim().replace(",", ""));
      } catch (Exception ex) {
        return dec;
      }

    return dec;
  }

  public static String convToMoney(Object pValue) {
    return String.format("%1$,.2f", new Object[] { convToDecimal(pValue) });
  }

  public static int stringToInt(String string)
  {
    String str = string.substring(0, string.indexOf("."));
    int intgeo = Integer.parseInt(str);
    return intgeo;
  }

  public static String fmtMicrometer(String text)
  {
    DecimalFormat df = null;
    if (text.indexOf(".") > 0)
      if (text.length() - text.indexOf(".") - 1 == 0)
        df = new DecimalFormat("###,##0.");
      else if (text.length() - text.indexOf(".") - 1 == 1)
        df = new DecimalFormat("###,##0.0");
      else
        df = new DecimalFormat("###,##0.00");

    else
      df = new DecimalFormat("###,##0");

    double number = 0D;
    try {
      number = Double.parseDouble(text);
    } catch (Exception e) {
      number = 0D;
    }
    return df.format(number);
  }

  public static int daysBetween(Date smdate, Date bdate)
    throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    smdate = sdf.parse(sdf.format(smdate));
    bdate = sdf.parse(sdf.format(bdate));
    Calendar cal = Calendar.getInstance();
    cal.setTime(smdate);
    long time1 = cal.getTimeInMillis();
    cal.setTime(bdate);
    long time2 = cal.getTimeInMillis();
    long between_days = (time2 - time1) / 86400000L;
    return Integer.parseInt(String.valueOf(between_days));
  }

  public static int sexForCard(String id_card)
  {
    if (id_card.length() == 18) {
      int i = Integer.parseInt(id_card.substring(16, 17));
      if (i % 2 == 0)
        return 0;

      return 1;
    }

    int i = Integer.parseInt(id_card.substring(14, 15));
    if (i % 2 == 0)
      return 0;

    return 1;
  }
}
