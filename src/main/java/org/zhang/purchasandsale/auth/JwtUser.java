package org.zhang.purchasandsale.auth;

import java.util.Date;
import lombok.Data;

/**
 * desc：jwt的内部使用的用户
 * 
 * @author G.fj @date:2019年3月26日 下午4:40:16
 *
 */
@Data
public class JwtUser {
	/** 企业id */
	private int eid;
	/** 用户id */
	private String uid;
	/** 用户登录名称 */
	private String userName;
	/** jwt的过期时间 */
	private long expire = 24 * 60 * 60 * 1000;
	/** jwt的签发时间 */
	private Date iat;
	/** 是否成功 */
	private boolean success;
	/** 解析返回来的错误信息 */
	private String error;
	/** 是否是用级 */
	private boolean system;

	public void wrapUser(User user) {
		this.eid = user.getEuid();
		this.uid = user.getId();
		this.userName = user.getUserName();
	}

	public User getUser() throws Exception {
		User user = new User();
		user.setEuid(this.eid);
		user.setId(this.uid);
		user.setUserName(this.userName);
		return user;
	}
}
