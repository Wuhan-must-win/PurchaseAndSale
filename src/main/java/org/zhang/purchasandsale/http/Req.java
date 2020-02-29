package org.zhang.purchasandsale.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @category 请求对象
 * @author G.fj
 *
 */
@Setter
@Getter
public class Req<T> {
	/** 是否是调试状态 */
	private boolean debug;
	private T param;
}
