package com.sino.base.common.util;

import com.sino.base.common.tree.TreeUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.Reflections;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;

public class JsonUtils {

	//	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	//	属性为默认值，不参与序列化
	private static JsonMapper binder = JsonMapper.buildNonDefaultMapper();

	// 以下是常见json返回值
	public final static String JSON_RESULT_SUCCESS = "{\"result\":\"success\"}";
	public final static String JSON_RESULT_FALSE = "{\"result\":\"false\"}";
	public final static String JSON_RESULT_FALSE_MSGMDL = "{\"result\":\"false\", \"message\":\"%s\"}";
	public final static String JSON_RESULT_MESSAGE = "{\"result\":\"success\", \"message\":\"%s\"}";
	public final static String JSON_RESULT_OUTTIME = "{\"result\":\"outtime\"}";

	public static String getJsonObjValue(Object obj) {
		return binder.toJson(obj);
	}

	//	参数说明：参数1：要转化的数组中的一个个值，参数2：实体属性数组中的一个个值
	public static String getJsonColValue(Object obj, String colStr) {
		//		实体属性数组中的每个值以@符号分割存放到colAtts数组中，例如这样的orgId@OrgOrganization@orgId@orgName
		String[] colAtts = colStr.split("@");
		//		参数说明：参数1：要转化的数组中的每一个值，参数2：实体属性数组中外键值切分后数组的第一个值（orgID）
		//		返回值：获取转化数组中“外键值”的实际值
		Object objCol = Reflections.getFieldValue(obj, colAtts[0]);

		if (colAtts.length == 1) {
			return binder.toJson(objCol);
		}

		String value = null;
		if (objCol != null) {
			switch (colAtts.length) {
				case 2:
					//查询数据字典项
					value = WebFuncUtils.getResValue(colAtts[1], objCol.toString());
					break;
				case 4:
					//				去“外键”自己表中条件查询
					value = WebFuncUtils.getObjColValue(colAtts[1], colAtts[2], colAtts[3], objCol.toString());
					break;
			}
		}
		//		将从“外键”表中查询的结果转成一个json字符串
		return binder.toJson(value);
	}


	// 转换树对象列表为json树信息
	// 参数nodeAttNames为指定要转换的树节点属性，以逗号间隔
	// 参数jsonAttNames为指定转换属性对应的json属性名称，个数及次序必须与nodeAttNames保持一致
	@SuppressWarnings("rawtypes")
	public static String getJsonTreeInfo(List listNode, String nodeAttNames, String jsonAttNames, String childName) {
		return getJsonTreeInfo(listNode, nodeAttNames, jsonAttNames, childName, TreeUtils.TREE_DEFAULT_COL_NAME,
				TreeUtils.TREE_DEFAULT_LEVEL_LENGTH);
	}

	// 转换树对象列表为json树信息
	// 参数nodeAttNames为指定要转换的树节点属性，以逗号间隔
	// 参数jsonAttNames为指定转换属性对应的json属性名称，个数及次序必须与nodeAttNames保持一致
	@SuppressWarnings("rawtypes")
	public static String getJsonTreeInfo(List listNode, String nodeAttNames, String jsonAttNames, String childName,
			String treeCode, int levelLength) {
		if (listNode == null) {
			return "[]";
		}
		String[] nodeAtts = nodeAttNames.split(",");
		String[] jsonAtts = (jsonAttNames == null || "".equals(jsonAttNames)) ? nodeAtts : jsonAttNames.split(",");
		assertEquals(nodeAtts.length, jsonAtts.length);

		int attNum = nodeAtts.length;

		String lastTreeCode = "";
		String curTreeCode = "";
		int nChild = 0;
		int nodeNum = listNode.size();

		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0; i < nodeNum; i++) {
			curTreeCode = Reflections.getFieldValue(listNode.get(i), treeCode).toString();
			if (i > 0) {
				if (curTreeCode.length() > lastTreeCode.length()) {
					sb.append(", \"").append(childName).append("\": [ ");
					nChild++;
				} else if (curTreeCode.length() < lastTreeCode.length()) {
					int nLevel = (lastTreeCode.length() - curTreeCode.length()) / levelLength;
					for (int k = 0; k < nLevel; k++) {
						sb.append("} ]");
						nChild--;
					}
					sb.append(" }, ");
				} else {
					sb.append(" }, ");
				}
			}
			for (int j = 0; j < attNum; j++) {
				String value = getJsonColValue(listNode.get(i), nodeAtts[j]);
				if (j == 0) {
					sb.append("{ \"");
				} else {
					sb.append(", \"");
				}
				sb.append(jsonAtts[j]).append("\":").append(value);
			}
			lastTreeCode = curTreeCode;
		}
		while (nChild > 0) {
			sb.append(" } ]");
			nChild--;
		}
		if (nodeNum > 0) {
			sb.append(" }");
		}
		sb.append(" ]");

		return sb.toString();
	}

	//	参数说明：参数1是要转换的数组中的一个个参数，参数2是实体属性数组，参数3是页面属性数组
	public static String getJsonObjInfo(Object obj, String[] objAtts, String[] jsonAtts) {
		//		nAtt是实体属性数组的长度
		int nAtt = objAtts.length;
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		for (int i = 0; i < nAtt; i++) {
			//			参数说明：参数1：要转化的数组中的一个个值，参数2：实体属性数组中的一个个值
			//			getJsonColValue的结果是从“外键”表中查询的结果转成的一个json字符串
			String value = getJsonColValue(obj, objAtts[i]);
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("\"").append(jsonAtts[i]).append("\":").append(value);
		}
		sb.append(" }");
		return sb.toString();
	}

	/**
	 * 将对象转换为json串
	 *
	 * @param obj          将要转换成json串的实体类对象
	 * @param ObjAttNames  实体类对应的字段名
	 * @param jsonAttNames 页面接收的json对应的字段名
	 */
	public static String getJsonObjInfo(Object obj, String ObjAttNames, String jsonAttNames) {
		String[] objAtts = ObjAttNames.split(",");
		String[] jsonAtts = (jsonAttNames == null || "".equals(jsonAttNames)) ? objAtts : jsonAttNames.split(",");
		assertEquals(objAtts.length, jsonAtts.length);
		for (int i = 0; i < jsonAtts.length; i++) {
			int n = jsonAtts[i].indexOf("@");
			if (n > 0) {
				jsonAtts[i] = jsonAtts[i].substring(0, n);
			}
		}

		return getJsonObjInfo(obj, objAtts, jsonAtts);
	}

	@SuppressWarnings("rawtypes")
	public static String getJsonListInfo(List listObj, String jsonName) {
		if (listObj == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		int nodeNum = listObj.size();
		for (int i = 0; i < nodeNum; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("{\"").append(jsonName).append("\":").append(binder.toJson(listObj.get(i))).append("}");
		}
		sb.append(" ]");
		return sb.toString();
	}

	//	public static<T> String getJsonPageInfo(Page<T> page, String nodeAttNames, String jsonAttNames)
	//	{
	//		if( page==null ){
	//			return "{}";
	//		}
	//		StringBuilder sb = new StringBuilder();
	//		sb.append("{ \"pageNo\":").append(page.getPageNo());
	//		sb.append(", \"pageSize\":").append(page.getPageSize());
	//		sb.append(", \"orderBy\":\"").append(page.getOrderBy()).append("\"");
	//		sb.append(", \"orderDir\":\"").append(page.getOrderDir()).append("\"");
	//		sb.append(", \"totalItems\":").append(page.getTotalItems());
	//		sb.append(", \"result\":").append(getJsonListInfo(page.getResult(), nodeAttNames, jsonAttNames));
	//		sb.append(" }");
	//		return sb.toString();
	//	}

	public static <T> String getJQJsonPageInfo(Page<T> page, String nodeAttNames, String jsonAttNames) {
		if (page == null) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"total\":").append(page.getTotalPages());
		sb.append(", \"page\":").append(page.getPageNo());
		sb.append(", \"records\":").append(page.getTotalItems());
		sb.append(", \"rows\":").append(getJsonListInfo(page.getResult(), nodeAttNames, jsonAttNames));
		sb.append(" }");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	//	参数说明：参数1：要转换的数组，参数2：实体属性字符串，参数3：页面属性字符串
	public static String getJsonListInfo(List listObj, String nodeAttNames, String jsonAttNames) {
		//如果要转换的数组的数据为空，就返回一个空的json数组
		if (listObj == null) {
			return "[]";
		}
		//		对实体属性以,分割成一个数组nodeAtts
		String[] nodeAtts = nodeAttNames.split(",");
		//		传给页面的json属性集合如果是空，那么jsonAttrs就是实体属性数组nodeAtts，如果不是空，那么就以,分割
		String[] jsonAtts = (jsonAttNames == null || "".equals(jsonAttNames)) ? nodeAtts : jsonAttNames.split(",");
		//		assertEquals属于org.junit.Assert包，判断实体属性数组和json属性数组是否的长度是否相等，如果不相等将抛出相应的信息。
		assertEquals(nodeAtts.length, jsonAtts.length);

		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		int nodeNum = listObj.size();
		for (int i = 0; i < nodeNum; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			//			参数说明：参数1是要转换的数组中的一个个参数，参数2是实体属性数组，参数3是页面属性数组
			sb.append(getJsonObjInfo(listObj.get(i), nodeAtts, jsonAtts));
		}
		sb.append(" ]");
		return sb.toString();
	}

	public static <T> String getJsonPageInfo(Page<T> page, String nodeAttNames, String jsonAttNames) {
		if (page == null) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"pageNo\":").append(page.getPageNo());
		sb.append(", \"pageSize\":").append(page.getPageSize());
		sb.append(", \"orderBy\":\"").append(page.getOrderBy()).append("\"");
		sb.append(", \"orderDir\":\"").append(page.getOrderDir()).append("\"");
		sb.append(", \"totalItems\":").append(page.getTotalItems());
		sb.append(", \"result\":").append(getJsonListInfo(page.getResult(), nodeAttNames, jsonAttNames));
		sb.append(" }");
		return sb.toString();
	}

	public static String getObjAttName(final String jsonAttName, final String objAttNames, final String jsonAttNames) {
		String[] objNames = objAttNames.split(",");
		String[] jsonNames = jsonAttNames.split(",");
		for (int i = 0; i < jsonNames.length; i++) {
			if (jsonNames[i].equals(jsonAttName)) {
				String[] segNames = objNames[i].split("@");
				return segNames[0];
			}
		}
		return jsonAttName;
	}

	public static String mapToJson(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			sb.append("\"").append(key).append("\":\"").append(value).append("\"");
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append(" }");
		return sb.toString();
	}

	public static String mapToJson(Hashtable<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			sb.append("\"").append(key).append("\":").append("\"").append(value).append("\"");
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append(" }");
		return sb.toString();
	}


	@SuppressWarnings("rawtypes")
	public static String propToJson(Properties props) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		Enumeration en = props.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String value = props.getProperty(key);
			sb.append("\"").append(key).append("\":").append("\"").append(value).append("\"");
			if (en.hasMoreElements()) {
				sb.append(", ");
			}
		}
		sb.append(" }");
		return sb.toString();
	}

	/**
	 * 从一个JSON数组得到一个java对象集合
	 *
	 * @param object
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getDTOList(String jsonString, Class clazz) {
		JSONArray array = JSONArray.fromObject(jsonString);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext(); ) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, clazz));
		}
		return list;
	}

	public static String object2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double || obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(list2json((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(set2json((Set<?>) obj));
		} else {
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	public static String bean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {}
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
			for (Object obj : list) {
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
			for (Object obj : array) {
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
			for (Object key : map.keySet()) {
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
			for (Object obj : set) {
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
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
				case '"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '/':
					sb.append("\\/");
					break;
				default:
					if (ch >= '\u0000' && ch <= '\u001F') {
						String ss = Integer.toHexString(ch);
						sb.append("\\u");
						for (int k = 0; k < 4 - ss.length(); k++) {
							sb.append('0');
						}
						sb.append(ss.toUpperCase());
					} else {
						sb.append(ch);
					}
			}
		}
		return sb.toString();
	}

	//对于多表联合查询，用于排序的映射
	public static String getOrderObjAttName(final String orderName, final String orderAttNames,
			final String realAttNames) {
		String[] orderNames = orderAttNames.split(",");
		String[] realNames = realAttNames.split("#");
		for (int i = 0; i < orderNames.length; i++) {
			if (orderNames[i].equals(orderName)) {
				return realNames[i];
			}
		}
		return null;
	}

}
