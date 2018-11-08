package com.itqmbc.common.config;
import java.util.Properties;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public final class Config extends PropertyPlaceholderConfigurer
{
  private static Config m_instance = new Config();
  private static Properties m_props = new Properties();

  protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
    throws BeansException
  {
    super.processProperties(beanFactoryToProcess, props);
    m_props = props;
  }

  public static Config getInstance()
  {
    return m_instance;
  }

  public static String getString(String key)
  {
    if (!(m_props.containsKey(key)))
      throw new RuntimeException(key + "没有定义。");

    return m_props.getProperty(key);
  }

  public static boolean getBoolean(String key)
  {
    return BooleanUtils.toBoolean(getString(key));
  }

  public static int getInt(String key)
  {
    String property = getString(key);
    if ((property == null) || (property.equals("")))
      throw new RuntimeException(key + "的值不能指定。");

    int i = 0;
    try {
      i = Integer.parseInt(property);
    } catch (NumberFormatException e) {
      throw new RuntimeException(key + "的值不能指定。", e);
    }
    return i;
  }

  public static long getLong(String key)
  {
    String property = getString(key);
    if ((property == null) || (property.equals("")))
      throw new RuntimeException(key + "的值不能指定。");

    long l = -3600855780162535424L;
    try {
      l = Long.parseLong(property);
    } catch (NumberFormatException e) {
      throw new RuntimeException(key + "的值不能指定。", e);
    }
    return l;
  }

  public static String[] getStringArray(String key)
  {
    if (!(m_props.containsKey(key)))
      throw new RuntimeException(key + "没有定义。");

    String property = m_props.getProperty(key);
    String[] array = property.split(",", -1);
    return array;
  }
}
