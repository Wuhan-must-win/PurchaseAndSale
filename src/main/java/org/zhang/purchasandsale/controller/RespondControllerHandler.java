package org.zhang.purchasandsale.controller;

import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zhang.purchasandsale.http.Res;

/**
 * 
 * @category 统一返回值处理
 * @author G.fj
 * @since 2019年12月24日 上午9:27:29
 *
 */
@RestControllerAdvice
public class RespondControllerHandler implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	/**
	 * 校验错误拦截处理
	 *
	 * @param exception 错误信息集合
	 * @return 错误信息
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object validationBodyException(MethodArgumentNotValidException exception) {
		BindingResult result = exception.getBindingResult();
		StringBuffer sb = new StringBuffer();
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			errors.forEach(p -> {
				FieldError fieldError = (FieldError) p;
				String name = fieldError.getField();
				String error = fieldError.getDefaultMessage();
				if (StringUtils.isEmpty(error)) {
					error = "未知错误";
				}
				sb.append("错误:").append(name).append(error);
			});

		}
		return Res.error(sb.toString());
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
		return body;
	}

//	@Override
//	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
//			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
//			org.springframework.http.server.ServerHttpResponse response) {
//		if (body instanceof Res) {
//			return body;
//		} else {
//			Res<?> res = new Res<>();
//			res.setResult(body);
//			return res;
//		}
//	}
}