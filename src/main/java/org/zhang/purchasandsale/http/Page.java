package org.zhang.purchasandsale.http;

import java.util.List;
import lombok.Data;

/**
 * 
 * @category 通用各种数据库持久层的分页组件的中间组件
 * @author G.fj
 * @since 2019年12月23日 下午7:47:59
 *
 */
@Data
public class Page {
	/** 当前页 */
	private int pageNumber;
	/** 每页多少条记录,即使不分页，查询前几条，该数据仍旧有效 */
	private int pageSize;
	/** 共多少页 */
	private int pageCount;
	/** 总共多少条 */
	private long rowCount;
	/** 记录数量 */
	private List<Object> rows;
}
