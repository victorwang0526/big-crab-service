package com.victor.wang.bigCrab.sf.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyJsonUtil {
	private static Log log = LogFactory.getLog(MyJsonUtil.class);

	public MyJsonUtil() {
	}

	public static String object2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (!(obj instanceof String) && !(obj instanceof Integer) && !(obj instanceof Float) && !(obj instanceof Boolean) && !(obj instanceof Short) && !(obj instanceof Double) && !(obj instanceof Long) && !(obj instanceof BigDecimal) && !(obj instanceof BigInteger) && !(obj instanceof Byte)) {
			if (obj instanceof Object[]) {
				json.append(array2json((Object[])obj));
			} else if (obj instanceof List) {
				json.append(list2json((List)obj));
			} else if (obj instanceof Map) {
				json.append(map2json((Map)obj));
			} else if (obj instanceof Set) {
				json.append(set2json((Set)obj));
			} else {
				json.append(bean2json(obj));
			}
		} else {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		}

		return json.toString();
	}

	public static String bean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;

		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException var7) {
			;
		}

		if (props != null) {
			for(int i = 0; i < props.length; ++i) {
				try {
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception var6) {
					;
				}
			}

			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}

		return json.toString();
	}

	public static String list2json(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			Iterator var3 = list.iterator();

			while(var3.hasNext()) {
				Object obj = var3.next();
				json.append(object2json(obj));
				json.append(",");
			}

			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}

		return json.toString();
	}

	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			Object[] var5 = array;
			int var4 = array.length;

			for(int var3 = 0; var3 < var4; ++var3) {
				Object obj = var5[var3];
				json.append(object2json(obj));
				json.append(",");
			}

			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}

		return json.toString();
	}

	public static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			Iterator var3 = map.keySet().iterator();

			while(var3.hasNext()) {
				Object key = var3.next();
				json.append(object2json(key));
				json.append(":");
				json.append(object2json(map.get(key)));
				json.append(",");
			}

			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}

		return json.toString();
	}

	public static String set2json(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			Iterator var3 = set.iterator();

			while(var3.hasNext()) {
				Object obj = var3.next();
				json.append(object2json(obj));
				json.append(",");
			}

			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}

		return json.toString();
	}

	public static String string2json(String s) {
		if (s == null) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();

			for(int i = 0; i < s.length(); ++i) {
				char ch = s.charAt(i);
				switch(ch) {
					case '\b':
						sb.append("\\b");
						continue;
					case '\t':
						sb.append("\\t");
						continue;
					case '\n':
						sb.append("\\n");
						continue;
					case '\f':
						sb.append("\\f");
						continue;
					case '\r':
						sb.append("\\r");
						continue;
					case '"':
						sb.append("\\\"");
						continue;
					case '/':
						sb.append("\\/");
						continue;
					case '\\':
						sb.append("\\\\");
						continue;
				}

				if (ch >= 0 && ch <= 31) {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");

					for(int k = 0; k < 4 - ss.length(); ++k) {
						sb.append('0');
					}

					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}

			return sb.toString();
		}
	}
}