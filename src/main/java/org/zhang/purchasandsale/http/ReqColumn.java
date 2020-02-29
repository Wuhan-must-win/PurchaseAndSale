package org.zhang.purchasandsale.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 对象的属性描述
 * 
 * @author G.fj
 *
 */
@Getter
@Setter
public class ReqColumn implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 列名 */
	private String name;
	/** 列别名 */
	private String alias;
	/** 函数名称 */
	private String func;
	/** 是同表树形 */
	private boolean tree;
	/** 子表 表格对象 */
	private ReqParamData child;

	public ReqColumn() {

	}

	public ReqColumn(String name, String alias, String func, ReqParamData child) {
		this.name = name;
		this.alias = alias;
		this.func = func;
		this.child = child;
	}

	public ReqParamData newTableAdded(String name, String alias) {
		ReqParamData table = new ReqParamData(name, alias);
		this.child = table;
		return table;
	}

	protected ReqColumn deepClone() {
		ReqColumn col = this;
		try {
			/** 序列化 */
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			/** 反序列化 */
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			col = (ReqColumn) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return col;
	}
}
