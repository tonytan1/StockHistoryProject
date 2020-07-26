package com.aqm.stock.Interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aqm.stock.annotation.AuthToken;
import com.aqm.stock.model.ResponseEntity;

import java.io.PrintWriter;
import java.lang.reflect.Method;

public class AuthTokenInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(AuthTokenInterceptor.class);

    private String httpHeaderName = "Authorization";

    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.getAnnotation(AuthToken.class) != null
                || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {

            // String token = request.getHeader(httpHeaderName);
            String token = request.getHeader(httpHeaderName);
            logger.info("Get token from request is {} ", token);
            // String username = "";
            if (token != null && token.length() != 0) {
                // TODO:
                // This place skip the details that get the token via username form JEDIS
                // For this project use "AQM666" as token for test
                if (token.equalsIgnoreCase("AQM666")) {
                    return true;
                }
            }
            PrintWriter out = null;
            try {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ResponseEntity res = new ResponseEntity(401, "Unauthorized", null);
                out = response.getWriter();
                response.setContentType("application/json");
                out.println(res.toString());

                return false;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}