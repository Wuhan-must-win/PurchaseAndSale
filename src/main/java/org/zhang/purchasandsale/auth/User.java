package org.zhang.purchasandsale.auth;

import lombok.Data;

/**
 * 
 * @category 全局通用用户
 * @author G.fj
 * 
 */
@Data
public class User {
	private static final long serialVersionUID = 1L;
	/** 用户的唯一Id */
	private String id;
	/** 企业租户id */
	private int euid;
	/** 用户登录名称 */
	private String userName;
}
