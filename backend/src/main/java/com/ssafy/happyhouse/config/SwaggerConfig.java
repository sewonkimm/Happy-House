package com.ssafy.happyhouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// swagger url
// http://localhost:8080/swagger-ui.html


@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("project title")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any()) 
				.paths(PathSelectors.ant("/**"))    
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("MY API")
				.description("MY API Reference for Developers")
				.termsOfServiceUrl("https://edu.ssafy.com")
				.license("MY License")
				.licenseUrl("https://sewonkimm.github.io/").version("1.0").build();
	}

}