package com.smxr.utils.jwtUtils;

import com.smxr.pojo.ResponseUtils;
import com.smxr.pojo.User;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 17:19
 * security登录成功处理类,返回jwt
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        //从authentication中获取用户信息
        final User userDetail = (User) authentication.getPrincipal();
        String name = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        log.info(authorities.toString());
//        log.info(authentication.getDetails().toString());
//        log.info(authentication.getCredentials().toString());
//        log.info(authentication.getPrincipal().toString());
        //生成jwt                                         userDetail.getUsername()
        String token =  JwtConfigUtils.createJwtToken(name);
        httpServletResponse.addHeader("token", "Bearer " + token);
        httpServletResponse.getWriter().write(ResponseUtils.Success(token,"登陆成功").toString());
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
}
