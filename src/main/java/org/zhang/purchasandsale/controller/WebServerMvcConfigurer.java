package org.zhang.purchasandsale.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 
 * @category 设置FastJson为系统Json转换器
 * @author G.fj
 * @since 2019年12月24日 上午9:28:06
 *
 */
@Configuration
public class WebServerMvcConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenHandlerInterceptor());
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 定义一个convert 转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2 添加fastjson 的配置信息 比如 是否要格式化 返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 解决乱码的问题
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		// supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		// supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
		// supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
		// supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		// supportedMediaTypes.add(MediaType.APPLICATION_PDF);
		// supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
		// supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
		// supportedMediaTypes.add(MediaType.APPLICATION_XML);
		// supportedMediaTypes.add(MediaType.IMAGE_GIF);
		// supportedMediaTypes.add(MediaType.IMAGE_JPEG);
		// supportedMediaTypes.add(MediaType.IMAGE_PNG);
		// supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
		// supportedMediaTypes.add(MediaType.TEXT_HTML);
		// supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
		// supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		// supportedMediaTypes.add(MediaType.TEXT_XML);
		fastConverter.setSupportedMediaTypes(supportedMediaTypes);
		converters.add(fastConverter);
	}
}
