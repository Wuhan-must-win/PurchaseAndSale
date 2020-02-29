package org.zhang.purchasandsale.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zhang.purchasandsale.http.Res;

/**
 * 
 * @category 全局异常拦截器
 * @author G.fj
 * @since 2019年12月24日 上午9:28:36
 *
 */
@ControllerAdvice
@ResponseBody
public class GobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	public Res<?> exceptionHandler(HttpServletRequest request, Exception e) {
		System.out.println("ControllerAdvice异常捕捉：" + e.getMessage());
		// 其余异常简单返回为服务器异常
		e.printStackTrace();
		String message = "不确定的异常，请联系系统管理员";
		if (e.getMessage() != null) {
			message = e.getMessage();
		}
		return Res.error(message).setResult(false);

	}
}