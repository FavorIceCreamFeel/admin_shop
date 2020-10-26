package com.smxr.utils.jwtUtils;

import com.smxr.pojo.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 17:16
 * Spring security登录失败处理类
 */
@Component
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        httpServletResponse.getWriter().write(ResponseUtils.Error(null,"登录失败").toString());
        printWriter.flush();
        printWriter.close();
    }
}
