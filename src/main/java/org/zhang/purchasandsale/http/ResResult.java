package org.zhang.purchasandsale.http;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.yunhesoft.tm4.core.util.ObjUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口返回的对象数据
 * 
 * @author G.fj
 *
 */
@Setter
@Getter
public class ResResult {
	/** 供前端判断类型所使用的标识，1 = ResResult类型 */
	private int mode = 1;
	/** 返回数据名称 */
	private String name;
	/** 返回的对象的属性列表 */
	private List<ReqColumn> columns = new ArrayList<ReqColumn>();
	/** 返回的对象的数据列表 */
	private List<List<Object>> rows = new ArrayList<List<Object>>();
	/** 接口返回的数据分页信息 */
	private ReqPage page;
	/** 返回的状态代码 */
	private int code;
	/** 返回的状态信息 */
	private String message;

	public ResResult() {

	}

	public ResResult(String name) {
		this.name = name;
	}

	public ResResult(String name, ReqPage page) {
		this.name = name;
		this.page = page;
	}

	/**
	 * @category 设置一个list<Object>为值
	 * 
	 *           <pre>
	 *           系统自动将List < Object > 对象转换到columns和rows模式
	 *           </pre>
	 * 
	 * @param list
	 * @return
	 */
	public ResResult setList(Object list) {
		try {
			this.wrapList(list);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			this.code = -1;
			this.message = e.getMessage();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			this.code = -1;
			this.message = e.getMessage();
		}
		return this;
	}

	/**
	 * @category 设置由Jpa返回的分页数据
	 * 
	 *           <pre>
	 *           系统自动将List < Object > 对象转换到columns和rows模式
	 *           </pre>
	 * 
	 * @param page
	 * @return
	 */
	public <T> ResResult setList(Page page) {
		this.setList(page.getRows());
		this.page = ObjUtils.convertTo(page, ReqPage.class);
		return this;
	}

	/**
	 * @category 手动组装ResResult时，设置其rows值
	 * @param row
	 * @return
	 */
	public <T> ResResult addRow(List<Object> row) {
		this.rows.add(row);
		return this;
	}

	/*********** 内部方法 *************/
	/**
	 * @category 封装List类型的数据
	 * @param data
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	/**
	 * @param <T>
	 * @category 封装List数据到本对象中
	 * @param data
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> ResResult wrapList(Object data) throws IllegalArgumentException, IllegalAccessException {
		if (data != null) {
			ResResult rslt = this.reflectList(data);
			this.columns = rslt.getColumns();
			this.rows = rslt.getRows();
			if (this.name == null || this.name.trim() == "") {
				this.name = "result";
			}
		}
		return this;
	}

	private ResResult reflectList(Object data) throws IllegalArgumentException, IllegalAccessException {
		ResResult rslt = new ResResult();
		if (data == null) {
			rslt.setMessage("反射数据为空");
			rslt.setCode(-1);
			return rslt;
		}
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) data;
		List<Field> fields = null;
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (i == 0) {
				fields = reflectField(object);
				rslt.wrapColumns(fields);
			}
			List<Object> row = new ArrayList<Object>();
			for (int j = 0; j < fields.size(); j++) {
				Field field = fields.get(j);
				field.setAccessible(true);
				Object obj = field.get(object);
				if (obj instanceof List) {
					/** 递归封装 */
					row.add(reflectList(obj));
				} else {
					row.add(obj);
				}
			}
			rslt.addRow(row);
		}
		return rslt;
	}

	/**
	 * @category 根据反射的对象属性包装列的列表
	 * @param fields
	 */
	public List<ReqColumn> wrapColumns(List<Field> fields) {
		List<ReqColumn> columns = new ArrayList<ReqColumn>();
		for (int i = 0; i < fields.size(); i++) {
			ReqColumn col = new ReqColumn();
			Field field = fields.get(i);
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			col.setName(field.getName());
			col.setAlias(field.getName());
			columns.add(col);
		}
		this.columns = columns;
		return columns;
	}

	public List<Field> reflectField(Object object) {
		Field[] field1 = object.getClass().getSuperclass().getDeclaredFields();
		Field[] field2 = object.getClass().getDeclaredFields();
		List<Field> fields = new ArrayList<Field>();
		for (int i = 0; i < field1.length; i++) {
			if ("serialVersionUID".equals(field1[i].getName())) {
				continue;
			}
			fields.add(field1[i]);
		}
		for (int i = 0; i < field2.length; i++) {
			if ("serialVersionUID".equals(field2[i].getName())) {
				continue;
			}
			fields.add(field2[i]);
		}
		return fields;
	}

}
