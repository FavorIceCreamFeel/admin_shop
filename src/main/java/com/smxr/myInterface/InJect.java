package com.smxr.myInterface;

import java.lang.annotation.*;

/**
 * @Author lzy
 * @Date 2020/12/16 0:12
 * @PC smxr
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Inherited
@Documented
public @interface InJect {
}
