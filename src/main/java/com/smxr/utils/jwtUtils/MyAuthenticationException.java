package com.smxr.utils.jwtUtils;

import com.smxr.pojo.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 17:13
 * Spring security其他异常处理类,比如请求路径不存在等，
 * 如果不配置此类，则Spring security默认会跳转到登录页面
 */
@Component
public class MyAuthenticationException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        httpServletResponse.getWriter().write(ResponseUtils.Error(null,"请求路径不存在").toString());
        printWriter.flush();
        printWriter.close();
    }
}
