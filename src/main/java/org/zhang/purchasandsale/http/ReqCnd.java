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
public class ReqCnd implements Serializable {
	private static final long serialVersionUID = 1L;
	private ReqColumn col;
	/** 条件 */
	private String cnd;
	/** 参数列表 */
	private List<Object> vals = new ArrayList<Object>();
}
