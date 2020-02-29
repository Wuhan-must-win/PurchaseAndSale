package org.zhang.purchasandsale.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @category 请求参数
 * @author G.fj
 *
 * @param <T>
 */
@Getter
@Setter
public class ReqParam<T> {
	/** 对象别名 */
	private String name;
	/** 分页信息 */
	private ReqPage page;
	/** 参数数据 */
	private T param;
}
