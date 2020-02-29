package org.zhang.purchasandsale.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
public class ReqParamData implements Serializable {
	/** 数据库名称或简称 */
	private static final long serialVersionUID = 1L;
	private String db;
	/** 表格名称 */
	private String name;
	/** 表格别名 */
	private String alias;
	/** 表格主键一维[列对象]数组[column...] */
	private List<ReqColumn> pkeys;
	/** 一维[列对象]数组，其中有可能是一个对象 ["",{n:列名,a:别名,...}] */
	private List<ReqColumn> columns;
	/** 二维[对象对象]数组[[1,"","${{date}}",{table}...],[....]...]or// {字段名:值....} */
	private List<List<Object>> values;
	/** 一维[排序对象]数组[{n:'name',v:'1'},{n:'name',v:'0'}] */
	private List<String> orderBys;
	/** 一维[列对象]数组 [{column}] */
	private List<String> groupBys;
	/** 一维[条件对象]数组 [{c:'and',n:'字段名',v:值},{}] */
	private List<List<ReqParamCnd>> wheres;
	/**
	 * 一维[条件对象]数组过滤条件[{c:'and',n:'字段名',v:值},{c:'and',n:'字段名',v:值},{c:'between',n:名,v:值,v1:值1}]
	 */
	private List<List<ReqParamCnd>> havings;
	/**
	 * 一维[表格对象]数组表连接[{typ:'join',typ:table,ons:[]},{typ:'left',t:table,ons:[]},{typ:'right',t:table,ons:[]}...]
	 */
	private List<ReqParamData> joins;
	/** 连接的方法 left:l,right:r,inner:i */
	private String joinMode;
	/** 一维[条件对象] */
	private List<Map<String, Object>> joinOns;
	/** 一维[表格对象]数组 表联合 [{table}...] */
	private List<ReqParamData> unions;
	/** 一维[列对象]数组 判断记录存在的关键字[列名...],更新:如果不存在，则创建// 插入：如果已存在，则更新数据 */
	private List<Object> exists;
	/** 一维[存储对象]数组 调用数据库存储过程 */

	private List<Object> calls;
	/** 分页对象{pageNo,pageSize} */
	private ReqPage page;
	/** 字符串类型 该表所执行的操作 c:插入 r:读取 u:更新 d:删除 p:存储 */
	private String action;
	/******* s 临时变量 ********/
	/** sql文 */
	private String sql;

	/************ Table构建方法 ***************/
	/**
	 * @category 增加列
	 * @param name
	 * @return
	 */
	public ReqParamData addColumns(String... colNames) {
		if (this.columns == null) {
			this.columns = new ArrayList<ReqColumn>();
		}
		for (int i = 0; i < colNames.length; i++) {
			ReqColumn col = new ReqColumn();
			col.setName(colNames[i]);
			col.setAlias(colNames[i]);
			this.columns.add(col);
		}
		return this;
	}

	public ReqParamData addColumns(ReqColumn... cols) {
		if (this.columns == null) {
			this.columns = new ArrayList<ReqColumn>();
		}
		for (int i = 0; i < cols.length; i++) {
			this.columns.add(cols[i]);
		}
		return this;
	}

	/**
	 * @category 增加join连接的表格
	 * @param joinMode   连接模式 i=inner l=left r=right
	 * @param joinTables
	 * @return
	 */
	public ReqParamData join(String joinMode, ReqParamData... joinTables) {
		if (this.joins == null) {
			this.joins = new ArrayList<ReqParamData>();
		}
		for (int i = 0; i < joinTables.length; i++) {
			joinTables[i].setJoinMode(joinMode);
			this.joins.add(joinTables[i]);
		}
		return this;
	}

	public ReqParamData joinInner(ReqParamData... joinTables) {
		return this.join("i", joinTables);
	}

	public ReqParamData joinLeft(ReqParamData... joinTables) {
		return this.join("l", joinTables);
	}

	public ReqParamData joinRight(ReqParamData... joinTables) {
		return this.join("R", joinTables);
	}

	/**
	 * @category 增加join的on的条件
	 * @param joinons
	 * @return
	 */
	public ReqParamData joinOn(Map<String, Object>... joinons) {
		if (this.joinOns == null) {
			this.joinOns = new ArrayList<Map<String, Object>>();
		}
		for (int i = 0; i < joinons.length; i++) {
			this.joinOns.add(joinons[i]);
		}
		return this;
	}

	/**
	 * @category 增加联合的表格
	 * @param unionTables
	 * @return
	 */
	public ReqParamData union(ReqParamData... unionTables) {
		if (this.unions == null) {
			this.unions = new ArrayList<ReqParamData>();
		}
		for (int i = 0; i < unionTables.length; i++) {
			this.unions.add(unionTables[i]);
		}
		return this;
	}

	/**
	 * @category 分组
	 * @param groupBys
	 * @return
	 */
	public ReqParamData groupBy(String... groupBys) {
		if (this.groupBys == null) {
			this.groupBys = new ArrayList<String>();
		}
		for (int i = 0; i < groupBys.length; i++) {
			this.groupBys.add(groupBys[i]);
		}
		return this;
	}

	/**
	 * @category 排序
	 * @param orderBys
	 * @return
	 */
	public ReqParamData orderBy(String... orderBys) {
		if (this.orderBys == null) {
			this.orderBys = new ArrayList<String>();
		}
		for (int i = 0; i < orderBys.length; i++) {
			this.orderBys.add(orderBys[i]);
		}
		return this;
	}

	/**
	 * @category 查找表最前limit条记录
	 * @param limit
	 * @return
	 */
	public ReqParamData limit(int limit) {
		this.newPage().setPageSize(limit);
		return this;
	}

	/**
	 * @category 创建where条件
	 * 
	 *           <pre>
	 * 如果需要如下示例:
	 *  (a=b)or(c=b) => table.wheres(ReqCnd.eq('a','b')).wheres(ReqCnd.or()).wheres(ReqCnd.eq('c','b'))
	 *           </pre>
	 * 
	 * @param cnds
	 * @return
	 */
	public ReqParamData wheres(ReqParamCnd... cnds) {
		if (this.wheres == null) {
			this.wheres = new ArrayList<List<ReqParamCnd>>();
		}
		List<ReqParamCnd> cnd = new ArrayList<ReqParamCnd>();
		for (int i = 0; i < cnds.length; i++) {
			cnd.add(cnds[i]);
		}
		this.wheres.add(cnd);
		return this;
	}

	/**
	 * @category 创建having条件
	 * 
	 *           <pre>
	 * 如果需要如下示例:
	 *  (a=b)or(c=b) => table.having(ReqCnd.eq('a','b')).having(ReqCnd.or()).having(ReqCnd.eq('c','b'))
	 *           </pre>
	 * 
	 * @param cnds
	 * @return
	 */
	public ReqParamData having(ReqParamCnd... cnds) {
		if (this.wheres == null) {
			this.wheres = new ArrayList<List<ReqParamCnd>>();
		}
		List<ReqParamCnd> cnd = new ArrayList<ReqParamCnd>();
		for (int i = 0; i < cnds.length; i++) {
			cnd.add(cnds[i]);
		}
		this.wheres.add(cnd);
		return this;
	}

	/**
	 * @category 增加数据
	 * @param values
	 * @return
	 */
	public ReqParamData values(List<Object>... values) {
		if (this.values == null) {
			this.values = new ArrayList<List<Object>>();
		}
		for (int i = 0; i < values.length; i++) {
			this.values.add(values[i]);
		}
		return this;
	}

	public ReqParamData values(Object... values) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < values.length; i++) {
			list.add(values[i]);
		}
		return this.values(list);
	}

	/**
	 * @category 创建一个列,并自动加入到表格列中
	 * @param name
	 * @return 返回创建的列
	 */
	public ReqColumn newColumnAdded(String name) {
		ReqColumn column = this.newColumn(name);
		this.columns.add(column);
		return column;
	}

	/**
	 * @category 创建一个列
	 * @param name
	 * @return 创建的列
	 */
	public ReqColumn newColumn(String name) {
		ReqColumn column = new ReqColumn();
		column.setName(name);
		column.setAlias(name);
		return column;
	}

	/**
	 * @category 插入或更新
	 * @return
	 */
	public ReqParamData save() {
		this.action = "uc";
		return this;
	}

	/**
	 * @category 插入记录
	 * @return
	 */
	public ReqParamData insert() {
		this.action = "c";
		return this;
	}

	/**
	 * @category 设置表格主键
	 * @param cols
	 * @return
	 */
	public ReqParamData keys(String... cols) {
		if (this.pkeys == null) {
			this.pkeys = new ArrayList<ReqColumn>();
		}
		for (int i = 0; i < cols.length; i++) {
			ReqColumn column = new ReqColumn();
			column.setAlias(cols[i]);
			column.setName(cols[i]);
			this.pkeys.add(column);
		}
		return this;
	}

	/**
	 * @category更新
	 * 
	 *             <pre>
	 *             根据主键进行更新
	 *             new ReqTable("b_zz","zzs") _
	 *             .cols("zzdm","zzmc","zzms") _
	 *             .keys("zzdm") _
	 *             .values([["001","z1","zz1"],["002","z2","zz2"]]) _
	 *             .update()
	 *             </pre>
	 * 
	 * @return
	 */
	public ReqParamData update() {
		this.action = "u";

		return this;
	}

	/**
	 * @category 删除
	 * 
	 *           <pre>
	 *           根据where条件进行删除
	 *           new ReqTable("b_zz", "zzs") _
	 *           .wheres(ReqCnd.eq("zzdm", "001001001")) _
	 *           .wheres(ReqCnd.or()) _
	 *           .delete() _
	 *           根据记录进行删除
	 *           new ReqTable("b_zz","zzs") _
	 *           .wheres(ReqCnd.ins("zzdm","00100202","023420424","023424")
	 *           .delete()
	 *           </pre>
	 * 
	 * @return
	 */
	public ReqParamData delete() {
		this.action = "d";
		return this;
	}

	/**
	 * 
	 * @category 创建分页
	 * @return
	 */
	public ReqPage newPage() {
		if (this.page == null) {
			this.page = new ReqPage();
		}
		return this.page;
	}

	/*********** SQL构建方法 *************/
	public void parseTreeColumn() {
		List<ReqColumn> cols1 = this.columns;
		for (int i = 0; i < cols1.size(); i++) {
			if (cols1.get(i).getChild() != null) {
				/** 如果此列有子表存在 */
				if (cols1.get(i).getChild().getName().equalsIgnoreCase(this.name)) {
					/** 如果列子表的名称与父表名称相同，则是属于同表树形 */
					List<ReqColumn> cols2 = cols1.get(i).getChild().getColumns();
					boolean has = false;
					for (int j = 0; j < cols2.size(); j++) {
						Object o1 = cols1.get(i).getName();
						Object oo = cols2.get(j).getName();
						if (cols1.get(i).getName().equalsIgnoreCase(cols2.get(j).getName())) {
							has = true;
						}
					}
					if (!has) {
						this.columns.get(i).getChild().getColumns().add(this.columns.get(i).deepClone());
					}
				}
			}
		}
	}

	public String genSql() {
		if ("c".equalsIgnoreCase(this.getAction())) {
			return genInsertSql();
		} else if ("cu".equalsIgnoreCase(this.getAction())) {
		} else if ("r".equalsIgnoreCase(this.getAction())) {
			return genSelectSql();
		} else if ("u".equalsIgnoreCase(this.getAction())) {
			return genUpdateSql();
		} else if ("d".equalsIgnoreCase(this.getAction())) {
			return gendeleteSql();
		} else if ("p".equalsIgnoreCase(this.getAction())) {
			return genCallSql();
		}
		return "";
	}

	public String genInsertSql() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(this.name).append("(").append(genInsColsSql(this.columns))
				.append(") values(");
		String f = "";
		for (int i = 0; i < this.columns.size(); i++) {
			sql.append(f).append("?");
			f = ",";
		}
		sql.append(")");
		return sql.toString();
	}

	private String gendeleteSql() {
		StringBuilder sql = new StringBuilder();
		sql.append("delete ").append(this.name);
		/** 生成条件语句 或 */
		StringBuilder where = null;
		if (this.wheres != null && this.wheres.size() > 0) {
			/** where条件语句 */
			where = new StringBuilder();
//			where.append(" where ").append(genCndSql("", this.wheres));
		} else if (this.havings != null && this.havings.size() > 0) {
			where = new StringBuilder();
			// having条件语句
//			where.append(" having ").append(genCndSql("", this.wheres));
		}
		if (where != null) {
			sql.append(where);
			return sql.toString();
		}
		return null;
	}

	/**
	 * @category 生成更新语句
	 * @return
	 */
	private String genUpdateSql() {
		StringBuilder sql = new StringBuilder();

		sql.append("update ").append(this.name).append(" set ");
		/** 字段顺序Map：字段名->坐标 */
		/** Map<String,Integer> colMap = new LinkedHashMap<String,Integer>(); */
		/** 字段名称 */
		if (this.columns != null && this.columns.size() > 0) {
			int colsize = this.columns.size();
			for (int i = 0; i < colsize; i++) {
				if (i > 0) {
					sql.append(",");
				}
				ReqColumn col = this.columns.get(i);
				sql.append(col.getName()).append("=?");
				/** colMap.put(col.getName(), i); */
			}
		} else {
			/**
			 * System.out.println("【错误】【生成更新语句】【" + Dates.getNowDateTimeStr() +
			 * "】：无可用字段名称！");
			 */
			return "";
		}

		if ((this.pkeys != null && this.pkeys.size() > 0) || (this.wheres != null && this.wheres.size() > 0)) {
			sql.append(" where ");

			if ((this.pkeys != null && this.pkeys.size() > 0)) {
				sql.append(genPkeysSql("", this.pkeys));

				if (this.wheres != null && this.wheres.size() > 0) {
					sql.append(" and ");
				}
			}
			if (this.wheres != null && this.wheres.size() > 0) {
				/** sql.append(genCndSql("", this.wheres)); */
			}
		} else {
			/**
			 * System.out.println("【错误】【生成更新语句】【" + Dates.getNowDateTimeStr() +
			 * "】：无可用更新条件！");
			 */
			return "";
		}

		return sql.toString();
	}

	private String genCallSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("{call ").append(this.name).append("(");
		String f = "";
		for (int i = 0; i < this.calls.size(); i++) {
			sb.append(f).append("?");
			f = ",";
		}
		sb.append(")}");
		return sb.toString();
	}

	private String genSelectSql() {
		/** 生成列查询语句 */
		StringBuilder cols = new StringBuilder();
		cols.append(genColsSql(this.alias, this.columns));
		// 生成join语句
		StringBuilder join = null;
		if (this.joins != null && this.joins.size() > 0) {
			join = new StringBuilder();
//			for (int i = 0; i < this.joins.size(); i++) {
//				JSONObject obj = (JSONObject) this.joins.get(i);
//				ReqTable t = JSON.toJavaObject((JSONObject) obj.get("table"), ReqTable.class);
//				String cnd = obj.getString("cnd");
//				// 将查询的join字段加入到主表查询中
//				cols.append(",").append(genColsSql(t.getAlias(), t.getColumns()));
//				if ("l".equalsIgnoreCase(cnd)) {
//					join.append(" left join ");
//				} else if ("r".equalsIgnoreCase("cnd")) {
//					join.append("  join ");
//				} else {
//					join.append(" join ");
//				}
//				join.append(t.getName()).append(" as ").append(t.getAlias()).append(" on ");
//				join.append(t.genCndSql(t.getAlias(), t.getJoinOns()));
//			}
		}
		/** 生成条件语句 或 */
		StringBuilder where = null;
		if (this.wheres != null && this.wheres.size() > 0) {
			// where条件语句
			where = new StringBuilder();
//			where.append(" where ").append(genCndSql("", this.wheres));
		} else if (this.havings != null && this.havings.size() > 0) {
			where = new StringBuilder();
			// having条件语句
//			where.append(" having ").append(genCndSql("", this.wheres));
		}
		/** 生成orderby语句 */
		StringBuilder orderby = null;
		if (this.orderBys != null && this.orderBys.size() > 0) {
			orderby = new StringBuilder();
//			orderby.append(" order by ").append(genOrderBySql(this.orderBys));
		}
		/** 生成groupby 语句 */
		StringBuilder groupby = null;
		if (this.groupBys != null && this.groupBys.size() > 0) {
			groupby = new StringBuilder();
			groupby.append(" group by ").append(genGroupBySql(this.groupBys));
		}
		/** 生成union语句 */
		StringBuilder union = null;
		if (this.unions != null && this.unions.size() > 0) {
			union = new StringBuilder();
			for (int i = 0; i < this.unions.size(); i++) {
				union.append(" union ").append(this.unions.get(i).genSql());
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(cols.toString()).append(" from ");
		sb.append(this.name).append(" as ").append(this.getAlias());
		if (join != null) {
			sb.append(join.toString());
		}
		if (where != null) {
			sb.append(where.toString());
		}
		if (orderby != null) {
			sb.append(orderby.toString());
		}
		if (groupby != null) {
			sb.append(groupby.toString());
		}
		if (union != null) {
			{
				sb.append(union.toString());
			}
		}
		this.sql = sb.toString();
		return this.sql;
	}

	/**
	 * @category 生成添加列的语句
	 * @return
	 */
	private String genInsColsSql(List<ReqColumn> cols) {
		if (cols == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String f = "";
		for (int i = 0; i < cols.size(); i++) {
			ReqColumn col = cols.get(i);
			sb.append(f).append(col.getName());
			f = ",";
		}
		return sb.toString();
	}

	/**
	 * @category 生成查询列的语句
	 * @return
	 */
	private String genColsSql(String alias, List<ReqColumn> cols) {
		if (cols == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String f = "";
		for (int i = 0; i < cols.size(); i++) {
			ReqColumn col = cols.get(i);
			if (col.getChild() == null) {
				if (col.getFunc() != null && col.getFunc().trim() != "") {
					sb.append(f).append(col.getFunc()).append("(").append(col.getName()).append(")").append(" as ")
							.append(col.getAlias());
				} else {
					sb.append(f).append(alias).append(".").append(col.getName()).append(" as ").append(col.getAlias());
				}
			} else {
				sb.append(f).append("'' as ").append(col.getName());
			}
			f = ",";
		}
		return sb.toString();
	}

	private Object parseCndVal(Object val) {
		Object value = new Object();
		if (val == null) {
			return val;
		}
		if (val instanceof JSONArray) {
			JSONArray arr = (JSONArray) val;
			StringBuilder sb = new StringBuilder();
			String f = "";
			for (int i = 0; i < arr.size(); i++) {
				Object v = arr.get(i);
				if (v instanceof JSONObject) {
					try {
						ReqParamData t = JSON.toJavaObject((JSONObject) v, ReqParamData.class);
						sb.append(t.genSql());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (v instanceof String) {
					if (v.toString().trim().startsWith(":")) {
						sb.append(f).append(v);
					} else {
						sb.append(f).append("'").append(v).append("'");
					}
				} else {
					sb.append(f).append(v);
				}
				f = ",";
			}
			value = sb.toString();
		} else if (val instanceof String) {
			if (val.toString().trim().startsWith(":")) {
				value = val;
			} else {
				value = "'" + val.toString() + "'";
			}
		} else {
			value = val;
		}
		return value;
	}

	/**
	 * @category 解析主键或列名的值追加到数据中（用于替换?）
	 * @return
	 */
	public List<List<Object>> parsePkeysValues() {
		List<List<Object>> finalData = new ArrayList<List<Object>>();

		/** pkeys（主键或列名）在columns（键值对的列名）中的坐标 */
		List<Integer> ids = new ArrayList<Integer>();
		/** 匹配获取坐标 */
		if (this.pkeys != null && this.pkeys.size() > 0) {
			int pkeysSize = this.pkeys.size();
			int columnsSize = this.columns.size();
			/** 查找主键对应的数据索引 */
			for (int i = 0; i < pkeysSize; i++) {
				for (int j = 0; j < columnsSize; j++) {
					if (this.pkeys.get(i).getName().equalsIgnoreCase(this.columns.get(j).getName())) {
						ids.add(j);
						break;
					}
				}
			}
		} else {
			finalData = this.values;
		}

		if (ids.size() > 0) {
			for (int i = 0; i < this.values.size(); i++) {
				List<Object> newList = new ArrayList<Object>();
				List<Object> list = this.values.get(i);
				/** 追加原数据到新列表 */
				newList.addAll(list);
				/** 新列表追加到终极列表 */
				finalData.add(newList);
				/** 将关键列或普通列的值追加到结尾 */
				for (int j = 0; j < ids.size(); j++) {
					Integer idx = ids.get(j);
					newList.add(list.get(idx));
				}
			}
		} else {
			finalData = this.values;
		}

		return finalData;
	}

	/**
	 * @category 解析主键或列名构建where条件
	 * @param alias 表别名
	 * @param pkeys 主键或列名
	 * @return
	 */
	private String genPkeysSql(String alias, List<ReqColumn> pkeys) {
		StringBuilder sb = new StringBuilder();

		// 列别名（table1.xxx）
		if (alias == null) {
			alias = "";
		} else {
			if (alias.trim() != "") {
				alias = alias + ".";
			}
		}
		// 根据列名构建语句
		if (pkeys != null && pkeys.size() > 0) {
			int size = pkeys.size();
			for (int i = 0; i < size; i++) {
				if (i > 0) {
					sb.append(" and ");
				}
				ReqColumn pk = pkeys.get(i);
				sb.append(alias).append(pk.getName() + "=?");
			}
		}

		return sb.toString();
	}

	private String genCndSql(String alias, List<ReqParamCnd> cnds) {
		if (alias == null) {
			alias = "";
		} else {
			if (alias.trim() != "") {
				alias = alias + ".";
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cnds.size(); i++) {
//			ReqCnd cnd = cnds.get(i);
//			Object val = parseCndVal(cnd.getVal());
//			Object val1 = parseCndVal(cnd.getVal1());
//			if ("(".equalsIgnoreCase(cnd.getCnd()) || ")".equals(val) || "or".equalsIgnoreCase(cnd.getCnd())
//					|| "and".equalsIgnoreCase(cnd.getCnd())) {
//				sb.append(cnd.getCnd()).append(" ");
//			} else if ("=".equalsIgnoreCase(cnd.getCnd()) || ">".equalsIgnoreCase(cnd.getCnd())
//					|| "<".equalsIgnoreCase(cnd.getCnd()) || ">=".equalsIgnoreCase(cnd.getCnd())
//					|| "<=".equalsIgnoreCase(cnd.getCnd()) || "!=".equalsIgnoreCase(cnd.getCnd())) {
//				sb.append(alias).append(cnd.getCol().getName()).append(" ").append(cnd.getCnd()).append(" ").append(val)
//						.append(" ");
//			} else if ("between".equalsIgnoreCase(cnd.getCnd())) {
//				sb.append(alias).append(cnd.getCol().getName()).append(" between ").append(val).append(" and ")
//						.append(val1).append(" ");
//			} else if ("in".equalsIgnoreCase(cnd.getCnd()) || "notin".equalsIgnoreCase(cnd.getCnd())) {
//				sb.append(alias).append(cnd.getCol().getName());
//				if ("notin".equalsIgnoreCase(cnd.getCnd()))
//					sb.append(" not in (");
//				else {
//					sb.append(" in (");
//				}
//				sb.append(val);
//				sb.append(")");
//			} else if ("like".equalsIgnoreCase(cnd.getCnd())) {
//				sb.append(alias).append(cnd.getCol().getName()).append(" like '%").append(cnd.getVal()).append("%'")
//						.append(" ");
//			} else if ("isnull".equalsIgnoreCase(cnd.getCnd())) {
//				sb.append(alias).append(cnd.getCol().getName()).append(" is null ");
//			}
		}
		return sb.toString();
	}

	private String genOrderBySql(List<Object> orders) {
		StringBuilder sb = new StringBuilder();
		String f = "";
		for (int i = 0; i < orders.size(); i++) {
			JSONObject obj = (JSONObject) orders.get(i);
			sb.append(" ").append(f).append(obj.getString("col"));
			if ("desc".equalsIgnoreCase(obj.getString("cnd")) || "d".equalsIgnoreCase(obj.getString("cnd"))) {
				sb.append(" desc ");
			} else {
				sb.append(" asc ");
			}
			f = ",";
		}
		return sb.toString();
	}

	private String genGroupBySql(List<String> groups) {
		StringBuilder sb = new StringBuilder();
		String f = "";
		for (int i = 0; i < groups.size(); i++) {
			sb.append(f).append(groups.get(i));
			f = ",";
		}
		return sb.toString();
	}

	public String replaceSqlParams(String sql) {
		String s = sql.replaceAll("(:)[\\w.]{1,}", "?");
		// 替换其中的以@打头的变量
		String str = "(\'@|\"@)[\\w]{1,}(.)[\\w]{1,}(\'|\")";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(s);
		while (m.find()) {
			String t = m.group().substring(2, m.group().length() - 1);
			s = s.replaceAll(m.group(), t);
		}
		return s;
	}

	public List<Object> parseSqlParamsList(String sql, Map<String, Object> vars, String palias, List<ReqColumn> pcols,
			List<Object> prow) {
		List<Object> list = new ArrayList<Object>();
		String str = "(:)[\\w.]{1,}";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(sql);
		while (m.find()) {
			String key = m.group();
			if (key.startsWith(":")) {
				key = key.substring(1);
				String[] s = key.split("\\.");
				if (s.length == 1) {
					/** 在vars中查找数据 */
					list.add(vars.get(key));
				} else if (s.length == 2) {
					/** 在父表中查找数据 */
					if (s[0].equalsIgnoreCase(palias) && prow != null && prow.size() > 0) {
						for (int i = 0; i < pcols.size(); i++) {
							if (s[1].equalsIgnoreCase(pcols.get(i).getName())) {
								list.add(prow.get(i));
								break;
							}
						}
					} else {
						list.add(null);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 判断变量
	 * 
	 * @param name
	 * @return
	 */
	protected Object processVar(String name) {
		Object value = name;
		name = name.replaceAll(" ", "");
		if (name.equalsIgnoreCase("${{tmuid}}")) {
//			value = TMUID.getUID();
		} else if (name.equalsIgnoreCase("${{datetime}}")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = sdf.format(new Date());
		} else if (name.equalsIgnoreCase("${{date}}")) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			value = sdf1.format(new Date());
		} else if (name.equalsIgnoreCase("${{month}}")) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			value = sdf2.format(new Date());
		}
		return value;
	}

	/*********** Getter/Setter *********/
	public ReqParamData() {

	}

	public ReqParamData(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	public ReqParamData(String name, String alias, List<ReqColumn> cols) {
		this.name = name;
		this.alias = alias;
		this.columns = cols;
	}

	protected ReqParamData deepClone() {
		ReqParamData t = this;
		try {
			/** 序列化 */
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			/** 反序列化 */
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

			ObjectInputStream ois = new ObjectInputStream(bis);
			t = (ReqParamData) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public Map<String, ReqColumn> mapCols() {
		Map<String, ReqColumn> map = new HashMap<String, ReqColumn>(16);
		for (int i = 0; i < this.columns.size(); i++) {
			map.put(this.columns.get(i).getName().toLowerCase(), this.columns.get(i));
		}
		return map;
	}

}
