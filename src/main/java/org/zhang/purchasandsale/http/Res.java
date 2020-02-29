package org.zhang.purchasandsale.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunhesoft.tm4.core.constant.CommonConstant;
import com.yunhesoft.tm4.core.util.ObjUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * @category 返回格式
 * @author G.fj
 *
 */
public class Res<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 返回的对象类型 map object */
	private String clazz = "object";
	/** 返回的数据，可能是一个对象，也可能是一个Map，包含多个ResResult对象 */
	@ApiModelProperty(value = "返回的数据")
	private Object result;
	@ApiModelProperty(value = "成功标志")
	private boolean success = true;

	@ApiModelProperty(value = "返回处理消息")
	private String message = "操作成功！";

	@ApiModelProperty(value = "返回代码")
	private Integer code = 0;

	@ApiModelProperty(value = "返回时间戳")
	private long timestamp = System.currentTimeMillis();

	/** 基本操作 */
	public Res() {

	}

	public Object getResult() {
		return this.result;
	}

	public Res<T> setResult(Object result) {
		if (result instanceof List) {
			this.clazz = "list";
		} else if (result instanceof Map) {
			this.clazz = "map";
		} else {
			this.clazz = "object";
		}
		this.result = result;
		return this;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public Res<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMessage() {
		return this.message;
	}

	public Res<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public int getCode() {
		return this.code;
	}

	public Res<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public Res<T> setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public Res<T> setClazz(String clazz) {
		this.clazz = clazz;
		return this;
	}

	public String getClazz() {
		return this.clazz;
	}

	private void put(String name, Object data) {
		if (this.result == null || !(this.result instanceof Map)) {
			this.result = new HashMap<String, Object>(16);
			this.clazz = "map";
		}
		((Map) this.result).put(name.toLowerCase(), data);
	}

	/********** 数据操作 ***************/

	/**
	 * @category 增加单独一个对象到Res中
	 * 
	 *           <pre>
	 *       	注：该对象返回到前端时，仍是一个对象
	 *       		但通过增加多个对象，同时可以返回多个对象
	 *           </pre>
	 * 
	 * @param name
	 * @param data
	 * @return
	 */
	public Res<T> add(String name, T data) {
		this.put(name, data);
		return this;
	}

	/**
	 * @category 增加一个列表对象到Res实例上
	 * 
	 *           <pre>
	 *   		该功能，主要是为将大数据量的列表对象，转换成二维表格
	 *     				主要功能是为减少网络传输量而开发
	 *           </pre>
	 * 
	 * @param name 前端显示的对象属性名(默认:result)
	 * @param list 可以是一个List<T>的数组，也可以是ResResult<T>对象
	 * @param page 分页信息
	 * @return
	 */
	public Res<T> addList(String name, List<?> list, ReqPage page) {
		if (name == null) {
			name = "result";
		}
		this.put(name.toLowerCase(), new ResResult(name, page).setList(list));
		return this;
	}

	/**
	 * @category 增加一个ResResult实例到Res实例中
	 * @param name
	 * @param data
	 * @param page
	 * @return
	 */
	public Res<T> addResult(String name, ResResult data, ReqPage page) {
		this.put(name.toLowerCase(), data);
		return this;
	}

	/**
	 * @category 增加一个不确定的列表对象
	 * @param data
	 * @return
	 */
	public Res<T> addList(List<?> data) {
		return this.addList("result", data, null);
	}

	/**
	 * @category 增加一个列表对象
	 * @param name 前端显示的对象属性名(默认:result)
	 * @param data
	 * @return
	 */
	public Res<T> addList(String name, List<?> data) {
		return this.addList(name, data, null);
	}

	/**
	 * @category 增加分页对象
	 * @param name 前端显示的对象属性名(默认:result)
	 * @param page
	 * @return
	 */
	public Res<T> addPage(String name, Page page) {
		ReqPage p = ObjUtils.convertTo(page, ReqPage.class);
		return this.addList(name, page.getRows(), p);
	}

	public Res<T> addPage(Page page) {
		ReqPage p = ObjUtils.convertTo(page, ReqPage.class);
		return this.addList("result", page.getRows(), p);
	}

	/**
	 * @category 微服务间调用时，反射JSON对象到实际对象
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	public <E> List<E> feignResultList(Class<E> clazz) {
		final List<E> list = new ArrayList<E>();
		if (this.result != null && this.result instanceof JSONArray) {
			((JSONArray) this.result).forEach(o -> {
				list.add(JSON.toJavaObject((JSONObject) o, clazz));
			});
		}
		return list;
	}

	/**
	 * @category 得到指定泛型的map(仅用feign)
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	public <E> Map<String, E> feignResultMap(Class<E> clazz) {
		Map<String, E> map = null;
		if (this.result instanceof JSONObject) {
			map = new HashMap<String, E>(16);
			for (Map.Entry entry : ((JSONObject) this.result).entrySet()) {
				E v = JSON.toJavaObject((JSONObject) entry.getValue(), clazz);
				map.put((String) entry.getKey(), v);
			}
		}
		return map;
	}

	public <E> E feignResultObject(Class<E> clazz) {
		if (this.result instanceof JSONObject) {
			return JSON.toJavaObject((JSONObject) this.result, clazz);
		} else {
			return (E) this.result;
		}
	}

	public static <T> T createInstance(Class<T> cls) {
		T obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			obj = null;
		}
		return obj;
	}

	public Res<T> setError(String message) {
		this.code = CommonConstant.SC_DATA_ERROR_509;
		this.message = message;
		return this;
	}

	public Res<T> setError(int code, String message) {
		this.code = code;
		this.message = message;
		return this;
	}

	/************* 标识处理 ***************/
	/**
	 * @category 服务器端逻辑或数据错误
	 * @param message
	 * @return
	 */
	public Res<T> error509(String message) {
		this.message = message;
		this.code = CommonConstant.SC_DATA_ERROR_509;
		this.success = false;
		return this;
	}

	/**
	 * @category token失效 500
	 * @param message
	 * @return
	 */
	public Res<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}

	/**
	 * @category 操作成功
	 * @param message
	 * @return
	 */
	public Res<T> success(String message) {
		this.message = message;
		this.code = CommonConstant.SC_OK_200;
		this.success = true;
		return this;
	}

	/**
	 * @category 数据或逻辑错误 509
	 * @param message
	 * @return
	 */
	public Res<T> fail(String message) {
		this.message = message;
		this.code = CommonConstant.SC_DATA_ERROR_509;
		this.success = false;
		return this;
	}

	/************** 静态方法 ************/
	/**
	 * @category 返回成功标识
	 * @return
	 */
	public static Res<?> ok() {
		Res<Object> r = new Res<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage("成功");
		return r;
	}

	/**
	 * @category 返回自定义成功消息
	 * @param msg
	 * @return
	 */
	public static Res<Object> ok(String msg) {
		Res<Object> r = new Res<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(msg);
		return r;
	}

	/**
	 * @category 设置数据并成功返回
	 * @param data 返回数据，可能是对象，也可能是List对象
	 * @return
	 */
	public static Res<Object> ok(Object data) {
		Res<Object> r = new Res<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}

	/**
	 * @category 返回错误信息,由拦截器负责组装返回结果
	 * @param msg
	 * @return
	 */
	public static Res<String> error(String msg) {
		return error(CommonConstant.SC_DATA_ERROR_509, msg);
	}

	/**
	 * @category 返回一个自定义错误对象
	 * @param code
	 * @param msg
	 * @return
	 */
	public static Res<String> error(int code, String msg) {
		Res<String> r = new Res<String>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	/**
	 * @category 无权限访问返回结果
	 * @param msg
	 * @return
	 */
	public static Res<?> noAuth(String msg) {
		return error(CommonConstant.SC_NO_AUTHZ, msg);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
