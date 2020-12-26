package com.ssafy.happyhouse.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.happyhouse.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	@Autowired
    private JwtService jwtService;
	
	public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	logger.info(request.getMethod() + " : " + request.getServletPath());
        
        // option ��û�� �ٷ� �����������.
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            // request�� parameter���� auth_token���� �Ѿ�� �༮�� ã�ƺ���.
            // String token = request.getParameter("auth_token");
            String token = request.getHeader("auth-token");
            if (token != null && token.length() > 0) {
                // ��ȿ�� ��ū�̸� ����, �׷��� ������ ���ܸ� �߻���Ų��.
                jwtService.checkValid(token);
                logger.info("��ū ��� ���� : {}", token);
                return true;
            } else {
                throw new RuntimeException("���� ��ū�� �����ϴ�.");
            }
        }
    }
}
