package org.zhang.purchasandsale.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @category 认证用户保持器
 * @author G.fj
 * 
 *         <pre>
*             创建时间：2019年11月20日 下午1:04:39
 *         </pre>
 */
@Component
public class AuthUserHolder {

	private static final ThreadLocal<JwtUser> CONTEXT = new ThreadLocal<>();
	/** 是否启用租户 */
	public static boolean TENANTABLE;

	/** 是否启用租户 */
	@Value("${tm4.tenant.enabled:true}")
	public void setTenantable(boolean tenantable) {
		System.out.println("tenantable===" + tenantable);
		TENANTABLE = tenantable;
	}

	public static void setUser(JwtUser user) {
		CONTEXT.set(user);
	}

	/**
	 * @category 登录用户(非内部用户,非system用户)
	 * @return
	 * @throws Exception
	 */
	public static User getUser() throws Exception {
		System.out.println(TENANTABLE);
		if (TENANTABLE) {
			JwtUser juser = getJwtUser();
			if (juser == null) {
				throw new Exception("没有登录，请登录");
			}
			if (!juser.isSuccess()) {
				throw new Exception(juser.getError());
			}
			if (juser.isSystem()) {
				throw new Exception("没有登录，请登录");
			}
			return juser.getUser();
		}
		// 如果不启用租户模式，则返回一个默认用户，避免用户调用用户信息出错。
		return new User();

	}

	/**
	 * @category 系统用户(system)
	 * @return
	 * @throws Exception
	 */
	public static User getSystemUser() throws Exception {
		JwtUser juser = CONTEXT.get();
		if (juser == null) {
			throw new Exception("没有登录，请登录");
		}
		if (!juser.isSuccess()) {
			throw new Exception(juser.getError());
		}
		if (!juser.isSystem()) {
			return null;
		}
		return juser.getUser();
	}

	/**
	 * @category 原初登录用户(JwtUser,可能包含错误信息)
	 * @return
	 */
	public static JwtUser getJwtUser() {
		if (TENANTABLE) {
			return CONTEXT.get();
		} else {
			return new JwtUser();
		}
	}

	/**
	 * @category 清除用户
	 */
	public static void clear() {
		CONTEXT.remove();
	}
}