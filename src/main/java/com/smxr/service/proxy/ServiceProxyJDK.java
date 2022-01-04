package com.smxr.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author lzy
 * @Date 2021/1/1 14:02
 * @PC smxr
 * JDK的方式实现动态代理
 * 代理工厂
 */
public class ServiceProxyJDK implements InvocationHandler {
    private Object objectInterface;
    //聚合代理类
    public ServiceProxyJDK(Object objectInterface) {
        this.objectInterface = objectInterface;
    }

    public Object getInterfaceProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), objectInterface.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        for (Object arg : args) {
//            System.out.println(method.getName()+"方法参数打印:"+arg.getClass().getTypeName()+" : "+arg.toString());
//        }
        log("前置");
        //执行前
        Object invoke = method.invoke(objectInterface,args);//执行
        //执行后
        log("后置");
        return invoke;
    }

    private void log(String str){
        System.out.println("ServiceProxyJDK: "+str+"执行");
    }
}
