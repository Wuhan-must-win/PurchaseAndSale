package org.zhang.purchasandsale.http;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数对象
 * 
 * <pre>
 * 既做为输入分页参数，同时也做输出分页参数对象
 * </pre>
 * 
 * @author G.fj
 *
 */
@Getter
@Setter
public class ReqPage {
	/** 当前页 */
	private int pageNumber;
	/** 每页多少条记录,即使不分页，查询前几条，该数据仍旧有效 */
	private int pageSize;
	/** 共多少页 */
	private int pageCount;
	/** 总共多少条 */
	private long rowCount;
}
