package com.crud.spring_crudApp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;




@Component
public class MyInterceptor implements HandlerInterceptor {
	
    @Autowired
    TestBean testBean;
    
	private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    
	@Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception {
        if (requestServlet.getRequestURI().matches("(\\/login\\/login+\\/?)")) {
        	return true;
        }
        
        boolean status = testBean.checkToken(requestServlet, responseServlet);
        
        return status;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "responseType", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
    
    @Component
    public static class TestBean {
        public boolean checkToken(HttpServletRequest request, HttpServletResponse responseServlet) {
        	try {
                String token = request.getParameter("token");
                System.out.println("Token found in request : "+token);
//	            if (token == null) {
//					responseServlet.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad Token");
//					String msg = "ERROR : Invalid request, Expecting token with the request!";
//	            	System.out.println(msg);
//	            	logger.error(msg);
//	            	return false;
//	            }
//	            String loggedInUser = ClientDataCache.mapTokenToUseridStr.get(token);
        		return true;
        		
        	} catch (Exception e) {
				e.printStackTrace();
			}
        	return false;
        }
    }
    
    @ControllerAdvice
    public static class MyExceptionHandler {
        @ExceptionHandler(MyException.class)
        @ResponseBody
        public Map<String, Object> handelr() {
            Map<String, Object> m1 = new HashMap<String, Object>();
            m1.put("status", "error");
            m1.put("message", "token expired or does not exists!");
            return m1;
        }
    }
    
    public static class MyException extends RuntimeException {
    	
	}
    
}
