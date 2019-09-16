package com.hongx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  //声明我们定义的注解是放在什么上面的  也就是作用域
@Retention(RetentionPolicy.SOURCE)  //声明我们定义的注解的生命周期   java--->class-->run
public @interface BindView {
    int value();
}












