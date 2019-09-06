package com.hongx.hxioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: fuchenming
 * @create: 2019-09-06 08:28
 */
public class ListenerInvocationHandler implements InvocationHandler {

    private Object activity;
    private  Method activityMethod;

    public ListenerInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在这里去调用被注解了的click()
        return activityMethod.invoke(activity,args);
    }
}
