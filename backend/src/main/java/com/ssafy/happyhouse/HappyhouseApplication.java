package com.ssafy.happyhouse;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.happyhouse.interceptor.JwtInterceptor;

@SpringBootApplication
public class HappyhouseApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(HappyhouseApplication.class, args);
	}

	@Autowired
	private JwtInterceptor jwtInterceptor;

	// JWTInterceptor�� ��ġ�Ѵ�.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/user/**") // �⺻ ���� ���
				.excludePathPatterns(Arrays.asList("/login"));// ���� ���� ���
	}

//  Interceptor�� �̿��ؼ� ó���ϹǷ� ������ Corss Origin ó���� ���ش�.
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
				.exposedHeaders("auth-token");
	}

}
