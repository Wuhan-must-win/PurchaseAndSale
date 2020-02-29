package org.zhang.purchasandsale.webconfig;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.yunhesoft.tm4.config.auth.AuthUserHolder;
import com.yunhesoft.tm4.config.auth.JwtUser;
import com.yunhesoft.tm4.config.auth.TokenUtils;

/**
 * 
 * @category 用户token拦截器
 * @author G.fj
 * @since 2019年12月24日 上午9:29:16
 *
 */
//@Configuration
public class UserHandlerInterceptor implements HandlerInterceptor {
	private static String TOKEN = "token";
	private static String NULLSTR = "null";
	private static String BEARER = "Bearer";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/** 用户请求 */
		String token = request.getParameter(TOKEN);
		if (StringUtils.isBlank(token) || NULLSTR.equalsIgnoreCase(token)) {
			token = request.getHeader("Authorization");
		}
		if (token != null && token.startsWith(BEARER)) {
			token = token.substring(6);
		}
		if (token != null && !NULLSTR.equalsIgnoreCase(token)) {
			JwtUser juser = TokenUtils.getTokenUser(token);
			if (juser == null || !juser.isSuccess()) {
				throw new Exception(juser.getError());
			} else {
				AuthUserHolder.setUser(juser);
				JwtUser jj = AuthUserHolder.getJwtUser();
				System.out.println(jj);
			}
		}
		System.out.println(new Date() + "  = " + token);
		/** 内部请求,优先外部请求 */
		String gtoken = request.getHeader("SystemAuthorizationg");
		if (token == null && !StringUtils.isBlank(gtoken)) {
			if (gtoken.startsWith(BEARER)) {
				gtoken = gtoken.substring(6);
			}
			JwtUser juser = TokenUtils.getTokenUser(gtoken);
			juser.setSystem(true);
			AuthUserHolder.setUser(juser);
		}
		return true;
	}
}