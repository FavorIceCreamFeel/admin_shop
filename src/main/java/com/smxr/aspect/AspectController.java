package com.smxr.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author lzy
 * @Date 2020/12/23 23:10
 * @PC smxr
 */
@Aspect
@Component
public class AspectController {
    @Before("@annotation(com.smxr.myInterface.InJect)")
    public void aspectBefore(){
        System.out.println("前置拦截处理。。。。。。");
    }

//    @Around("execution(public * com.smxr.controller.AdminShopController.*(..))")
    @Around("@annotation(com.smxr.myInterface.InJect)")//使用注解进行耗时统计
    public Object aspectAround(ProceedingJoinPoint proceedingJoinPoint){
            long start=System.currentTimeMillis();
        Object proceed=null;
        try {
             proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end=System.currentTimeMillis();
        System.out.println("---------耗时："+(end-start)+"毫秒--------");
        return proceed;
    }
//    @After(Pro
}
