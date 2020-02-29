package org.zhang.purchasandsale.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @category 查询等条件类
 * @author G.fj @date:2019年5月30日 下午4:30:41
 *
 */
@Getter
@Setter
public class ReqParamCnd implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 所属的表格名称，如在做多表关联时，用以区别该字段是哪一个表格 */
	private String table;
	/** 条件类型,各种条件符号 */
	private String cnd;
	/** 列对象 */
	private ReqColumn col;
	/** 基本类型，数组[基本类型...]，查询表格(返回一个列) */
	private List<Object> val;

	/**
	 * 小于 lt （less than）
	 * 
	 * @param {Object} col 列
	 * @param {Object} value 值
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd newCnd(String condition, String col, Object... values) {
		ReqColumn column = new ReqColumn();
		column.setName(col);
		column.setAlias(col);
		return ReqParamCnd.newCnd(condition, column, values);
	}

	static public ReqParamCnd newCnd(String cndition, ReqColumn column, Object... values) {
		ReqParamCnd cnd = new ReqParamCnd();
		cnd.val = new ArrayList<Object>();
		cnd.setCnd(cndition);
		cnd.setCol(column);
		for (int i = 0; i < values.length; i++) {
			cnd.val.add(values[i]);
		}
		return cnd;
	}

	static public ReqParamCnd lt(String col, Object value) {
		return ReqParamCnd.newCnd("<", col, value);
	}

	/**
	 * le （less than or equal to） 小于等于
	 * 
	 * @param {Object} col
	 * @param {Object} value
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd le(String col, Object value) {
		return ReqParamCnd.newCnd("<=", col, value);
	}

	/**
	 * eq （equal to）等于
	 * 
	 * @param {Object} col
	 * @param {Object} value
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd eq(String col, Object value) {
		return ReqParamCnd.newCnd("=", col, value);
	}

	/**
	 * ne（ not equal to） 不等于
	 * 
	 * @param {Object} col
	 * @param {Object} value
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd ne(String col, Object value) {
		return ReqParamCnd.newCnd("!=", col, value);
	}

	/**
	 * ge（ greater than or equal to） 大于等于
	 * 
	 * @param {Object} col
	 * @param {Object} value
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd ge(String col, Object value) {
		return ReqParamCnd.newCnd(">=", col, value);
	}

	/**
	 * gt（ greater than） 大于
	 * 
	 * @param {Object} col
	 * @param {Object} value
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd gt(String col, Object value) {
		return ReqParamCnd.newCnd(">", col, value);
	}

	/**
	 * ins(列，参数....) 包含 因in是内置函数 注：参数也有可能是返回一个列的
	 * 
	 * @param {Object} col
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd ins(String col, Object... values) {
		return ReqParamCnd.newCnd("in", col, values);
	}

	/**
	 * 不包含 notIn(列,参数...) 注:参数也有可能是返回一个列的表格
	 * 
	 * @param {Object} col
	 */
	static public ReqParamCnd notIn(String col, Object... values) {
		return ReqParamCnd.newCnd("notin", col, values);
	}

	/**
	 * like 包含
	 * 
	 * @param {Object} col
	 * @param {Object} value
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd like(String col, Object value) {
		return ReqParamCnd.newCnd("like", col, value);
	}

	/**
	 * between 之间
	 * 
	 * @param {Object} col
	 * @param {Object} value
	 * @param {Object} value1
	 * @return {Condition} 条件对象
	 */
	static public ReqParamCnd between(String col, Object value, Object value1) {
		return ReqParamCnd.newCnd("between", col, value, value1);
	}

	/**
	 * 列值等于空(null)
	 * 
	 * @param {Object} col
	 */
	static public ReqParamCnd isnull(String col) {
		return ReqParamCnd.newCnd("isnull", col);
	}

	/**
	 * 或者
	 * 
	 * @return
	 */
	static public ReqParamCnd or() {
		return ReqParamCnd.newCnd("or", "");
	}

	/**
	 * 并且
	 * 
	 * @return
	 */
	static public ReqParamCnd and() {
		return ReqParamCnd.newCnd("and", "");
	}
}
