package org.zhang.purchasandsale.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @category swagger2的配置信息，其配置内容从配置信息中读取
 * @author G.fj
 * @since 2019年12月24日 上午9:28:55
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer {
	@Value("${tm4.swagger2.title:接口API}")
	private String title;
	@Value("${tm4.swagger2.description: }")
	private String description;
	@Value("${tm4.swagger2.version:0.0.1}")
	private String version;
	@Value("${tm4.swagger2.basepackage: }")
	private String basepackage;

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title).description(description).version(version).build();
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(basepackage)).paths(PathSelectors.any()).build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}