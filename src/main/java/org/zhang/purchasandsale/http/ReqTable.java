package org.zhang.purchasandsale.http;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @category 请求参数的表格
 * @author G.fj at 2019年7月4日
 *
 */
@Getter
@Setter
public class ReqTable implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 字符串类型 该表所执行的操作 c:插入 r:读取 u:更新 d:删除 p:存储 */
	private String action;
	/** 数据库名称或简称 */
	private String db;
	/** 表格名称 */
	private String name;
	/** 表格别名 */
	private String alias;
	/** 主键列 */
	private List<ReqColumn> pkeys;
	/** 表格列 */
	private List<ReqColumn> columns;
	/** 二维数据 */
	private List<List<Object>> values;
	/** 排序字段列表 */
	private List<ReqOrder> orders;
	/** 分组列表 */
	private List<String> groups;
	/** 检索条件 */
	private List<ReqCnds> wheres;
	/** haveing条件 */
	private List<ReqCnds> havings;
	/** join表格 */
	private List<ReqTable> joins;
	/** 连接的方法 left:l,right:r,inner:i,full:f */
	private String joinm;
	/** 一维[条件对象] */
	private List<ReqCnds> joinons;
	/** 联合表 */
	private List<ReqTable> unions;
	/** 一维[存储对象]数组 调用数据库存储过程 */
	private List<Object> calls;
	/** 分页信息 */
	private ReqPage page;
}
