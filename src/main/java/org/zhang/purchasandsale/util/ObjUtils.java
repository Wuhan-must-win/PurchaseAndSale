package org.zhang.purchasandsale.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @category 对象操作工具
 * @author G.fj
 * @since 2019年12月23日 下午7:49:01
 *
 */
public class ObjUtils {
	/**
	 * @category 从一对象copy到另一个对象(字段名相同)
	 * @param clazz 返回对象类型
	 * @param src   源对象
	 * @return 返回 T 对象
	 */
	public static <T> T convertTo(Object src, Class<T> clazz) {
		return convertTo(src, clazz, "");
	}

	/**
	 * @category 从一对象copy到另一个对象(字段名相同)
	 * @param clazz 返回对象类型
	 * @param src   源对象
	 * @param names 字段名对，用于源对象和目标对象的字段对应关系，字段顺序随意
	 * 
	 *              <pre>
	 *              示例:
	 *              如源对象的字段名为 name 而目标对象的字段名为username，则可以使用 
	 *              注:names，必须成对使用
	 *              convertTo(User.class,srcUser,"name","username")
	 *              </pre>
	 * 
	 * @return 返回 T 对象
	 */
	public static <T> T convertTo(Object src, Class<T> clazz, String... names) {
		Map<String, String> maps = null;
		if (names.length > 1) {
			maps = new HashMap<String, String>(16);
			for (int i = 0; i < names.length - 1; i = i + 2) {
				maps.put(names[i], names[i + 1]);
				maps.put(names[i + 1], names[i]);
			}
		}
		try {
			T des = clazz.newInstance();
			Map<String, Field> srcs = reflectField(src);
			Map<String, Field> dess = reflectField(des);
			for (Entry<String, Field> entry : srcs.entrySet()) {
				String sn = entry.getKey();
				Field sf = entry.getValue();
				if (maps != null) {
					String sn1 = maps.get(sn);
					if (sn1 != null) {
						sn = sn1;
					}
				}
				Field df = dess.get(sn);
				if (df != null) {
					try {
						sf.setAccessible(true);
						Object value = sf.get(src);
						sf.setAccessible(false);
						df.setAccessible(true);
						try {
							df.set(des, value);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
						df.setAccessible(false);
					} catch (IllegalArgumentException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
			return des;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @category 反射对象所有的属性
	 * @param object
	 * @return
	 */
	private static Map<String, Field> reflectField(Object object) {
		Field[] field1 = object.getClass().getSuperclass().getDeclaredFields();
		Field[] field2 = object.getClass().getDeclaredFields();
		Map<String, Field> fields = new HashMap<String, Field>(16);
		for (int i = 0; i < field1.length; i++) {
			if ("serialVersionUID".equals(field1[i].getName())) {
				continue;
			}
			fields.put(field1[i].getName().toLowerCase(), field1[i]);
		}
		for (int i = 0; i < field2.length; i++) {
			if ("serialVersionUID".equals(field2[i].getName())) {
				continue;
			}
			fields.put(field2[i].getName().toLowerCase(), field2[i]);
		}
		return fields;
	}

	/**
	 * @category 判断object是否为空,集合会校验size
	 */
	public static boolean isNull(Object... objs) {
		for (Object obj : objs) {
			if (ObjUtils.isEmpty(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @category 判断object是否不为空,集合会校验size
	 */
	public static boolean isNotNull(Object... obj) {
		return !ObjUtils.isNull(obj);
	}

	/**
	 * @category 对象非空判断
	 */
	public static boolean isNotEmpty(Object obj) {
		return !ObjUtils.isEmpty(obj);
	}

	/**
	 * @category 对象空判断
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}
		// else
		return false;
	}
}
