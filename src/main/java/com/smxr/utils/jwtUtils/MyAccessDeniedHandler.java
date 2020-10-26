package com.smxr.utils.jwtUtils;

import com.smxr.pojo.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 17:08
 * Spring security权限不足处理类
 * 只有登录后（即接口有传token）接口权限不足才会进入AccessDeniedHandler，
 * 如果是未登陆或者会话超时等，不会触发AccessDeniedHandler，而是会直接跳转到登陆页面。
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        httpServletResponse.getWriter().write(ResponseUtils.Error(null,"权限不足").toString());
        printWriter.flush();
        printWriter.close();
    }
}
