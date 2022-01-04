package com.smxr;

import com.smxr.controller.AdminShopController;
import com.smxr.myInterface.InJect;
import com.smxr.service.UserService;
import com.smxr.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @Author lzy
 * @Date 2020/12/15 21:57
 * @PC smxr
 */
@SpringBootTest
public class Spring {
    @Test
    void spring_12_15() throws Exception {

 /*Class类的常用方法：
getName()
一个Class对象描述了一个特定类的属性，Class类中最常用的方法getName以 String 的形式返回此 Class 对象所表示的实体（类、接口、数组类、基本类型或 void）名称。
newInstance()
Class还有一个有用的方法可以为类创建一个实例，这个方法叫做newInstance()。例如：x.getClass.newInstance()，创建了一个同x一样类型的新实例。newInstance()方法调用默认构造器（无参数构造器）初始化新建对象。
getClassLoader()
返回该类的类加载器。
getComponentType()
返回表示数组组件类型的 Class。
getSuperclass()
返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的超类的 Class。
isArray()
判定此 Class 对象是否表示一个数组类
。*/
        //获取类类对象 三种方式
        //1 有类对象获取
        AdminShopController adminShopController = new AdminShopController();
        Class<? extends AdminShopController> aClass = adminShopController.getClass();
        System.out.println(aClass.getName());//com.smxr.controller.AdminShopController
        //2 由全类名获取
        Class<?> adminShopController1 = Class.forName("com.smxr.controller.AdminShopController");
        System.out.println(adminShopController1.getName());//com.smxr.controller.AdminShopController
        //3 由 类.class 获取
        Class<AdminShopController> adminShopControllerClass = AdminShopController.class;
        System.out.println(adminShopControllerClass);//class com.smxr.controller.AdminShopController


        AdminShopController o = (AdminShopController) adminShopController1.newInstance();
//        System.out.println(o.getName());//my name is AdminShopController.class
        //获取简单类名
        System.out.println(aClass.getSimpleName());//AdminShopController
    }

    @Test
    void spring_12_16() throws Exception {
//属性

        AdminShopController adminShopController = new AdminShopController();
        Class<? extends AdminShopController> aClass = adminShopController.getClass();
        UserService userService = new UserServiceImpl();
        //获取所代表类的所有属性
        Field[] declaredFields = aClass.getDeclaredFields();//方法返回所有可访问的公共字段在类中声明或继承自超类。
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.toString());
            // 完整 ->  属性的类型  和 属性名
            //private com.smxr.service.UserService com.smxr.controller.AdminShopController.userService
            //private java.lang.String com.smxr.controller.AdminShopController.name
            System.out.println(declaredField.getName()); //属性名
        }
        //由属性名字获取这个属性
        Field userService1 = aClass.getDeclaredField("userService");
        //private com.smxr.service.UserService com.smxr.controller.AdminShopController.userService
        System.out.println("aClass.getDeclaredField() :" + userService1.toString());
        userService1.setAccessible(true);
        System.out.println("----------:" + userService1.getName());

        //获取所代表类 公开(public)的属性
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            System.out.println(field.toString());
            System.out.println(field.getName());
        }
//        由属性名字获取公开的这个属性
//        aClass.getField("userService")

    }

    @Test
    void spring_12_17() throws Exception {
        //方法


        AdminShopController adminShopController = new AdminShopController();
        Class<? extends AdminShopController> aClass = adminShopController.getClass();
        UserService userService = new UserServiceImpl();

        //获取所代表类的 所有方法;    方法返回该类的所有可访问的公共方法无论从类中还是继承自超类。
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        //由 方法名 和 参数类型;通过方法名和参数类型获取Method对象。
        String MethodName = "setUserService";
        Method setUserService = aClass.getMethod(MethodName, UserServiceImpl.class);
        //方法返回所有只在中声明的方法该类(不包括从超类继承的方法)。
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }
        //通过方法名和参数类型获取Method对象。
        Method setUserService1 = aClass.getDeclaredMethod(MethodName, UserServiceImpl.class);
        System.out.println("aClass.getDeclaredMethod" + setUserService1.toString());
        setUserService1.invoke(adminShopController, userService);

//        boolean b = adminShopController.getUserService().selectUserByPhoneNumber("123456789");
//        System.out.println(b);

    }

    @Test
    void spring_12_19() {
        AdminShopController adminShopController = new AdminShopController();
        Class<AdminShopController> adminShopControllerClass = (Class<AdminShopController>) adminShopController.getClass();
        Stream.of(adminShopControllerClass.getDeclaredFields()).forEach(File -> {
            String name = File.getName();
            InJect annotation = File.getAnnotation(InJect.class);
            if (annotation != null) {
                File.setAccessible(true);
                try {
                    Object o = File.getType().newInstance();
                    File.set(adminShopController, o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
//        System.out.println(adminShopController.getUserService().selectUserByPhoneNumber("1213"));
    }

//    @Test
    void spring_12_20() throws Exception {
        UserServiceImpl userServeice = new UserServiceImpl();

        AdminShopController controller = new AdminShopController();
        Class<? extends AdminShopController> aClass = controller.getClass();
        System.out.println(aClass.getSimpleName()+":"+aClass.getName());
        Class<AdminShopController> adminShopControllerClass = AdminShopController.class;
        System.out.println(adminShopControllerClass.getSimpleName()+":"+adminShopControllerClass.getName());
        Class<?> aClass1 = Class.forName("com.smxr.controller.AdminShopController");
        System.out.println(aClass1.getSimpleName()+":"+aClass1.getName());

        AdminShopController adminShopController = aClass.newInstance();
//        System.out.println("controller.getUserService().selectUserByPhoneNumber(): "+controller.getUserService().selectUserByPhoneNumber(""));

        Field userService = aClass.getDeclaredField("userService");
        userService.setAccessible(true);
        userService.set(controller,userServeice);
//        boolean b = controller.getUserService().selectUserByPhoneNumber("");
//        System.out.println("controller.getUserService().selectUserByPhoneNumber(): "+b);
        // 方法名 和 方法参数类型
        Method spring_test = aClass.getDeclaredMethod("spring_test");
        spring_test.setAccessible(true);
        //返回值为方法的返回值 void:null 其他的为object
        Object invoke = spring_test.invoke(controller);
    }
//    @Test
    void spring_12_21() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println(start);
        AdminShopController aClass = new AdminShopController();
        Stream.of(aClass.getClass().getDeclaredFields()).forEach(field -> {
            if (field.getAnnotation(InJect.class)!=null) {
                field.setAccessible(true);//返回值：字段的声明类型的Class对象。
                try {
                    field.set(aClass,field.getType().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        long end =  System.currentTimeMillis();
        System.out.println(end);
        System.out.println("耗时:"+String.valueOf(end-start)+"毫秒");
//        System.out.println("InJect.class:"+aClass.getUserService().selectUserByPhoneNumber(""));
    }
        @Test
    void spring_12_25() throws Exception {
            System.out.println(System.getProperties());
        }

}
