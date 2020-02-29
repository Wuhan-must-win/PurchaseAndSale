package org.zhang.purchasandsale.http;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author G.fj
 * @since 2019年12月23日 下午7:48:40
 *
 */
@Getter
@Setter
public class ReqCnds {
	private String cnd;
	private List<ReqCnd> cnds;
}
