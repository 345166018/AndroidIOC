package com.hongx.hxioc;

import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: fuchenming
 * @create: 2019-09-03 16:30
 */
public class InjectUtils {

    public static void inject(Object context) {
        injectLayout(context);
        injectView(context);
        injectEvent(context);
    }


    /**
     * 事件注入
     */
    private static void injectEvent(Object context) {
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
//            OnClick onClick = method.getAnnotation(OnClick.class);
            //得到方法上的所有注解
            Annotation[] annotations = method.getAnnotations();

            for (Annotation annotation : annotations) {

//                annotation  ===OnClick  OnClick.class
                Class<?> annotionClass = annotation.annotationType();
                EventBase eventBase = annotionClass.getAnnotation(EventBase.class);
                //如果没有eventBase，则表示当前方法不是一个处理事件的方法
                if (eventBase == null) {
                    continue;
                }
                //开始获取事件处理的相关信息，
                // 用于确定是哪种事件(onClick还是onLongClick)以及由谁来处理
                //订阅
                String listenerSetter = eventBase.listenerSetter();
                //事件（事件监听的类型）
                Class<?> listenerType = eventBase.listenerType();
                //事件处理   事件被触发之后，执行的回调方法的名称
                String callBackMethod = eventBase.callbackMethod();

//                         textView.setOnClickListener（new View.OnClickListener() {
//                              @Override
//                              public void onClick(View v) {
//
//                              }
//                          });

//                int[] value1=OnClick.value();//这就写死了

                Method valueMethod = null;
                try {
                    //反射得到ID,再根据ID号得到对应的VIEW
                    valueMethod = annotionClass.getDeclaredMethod("value");
                    int[] viewId = (int[]) valueMethod.invoke(annotation);
                    for (int id : viewId) {
                        Method findViewById = clazz.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(context, id);
                        if (view == null) {
                            continue;
                        }
                        //得到ID对应的VIEW以后
                        //开始在这个VIEW上执行监听  (使用动态代理)
                        //需要执行activity上的onClick方法
                        //activity==context       click==method
                        ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(context, method);
                        //proxy======View.OnClickListener()对象
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, listenerInvocationHandler);

                        //执行方法                                   setOnClickListener,new View.OnClickListener()
                        Method onClickMethod = view.getClass().getMethod(listenerSetter, listenerType);
                        onClickMethod.invoke(view, proxy);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

    /**
     * 控件注入
     */
    private static void injectView(Object context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int valueId = viewInject.value();

                try {
                    //反射执行findViewById
                    Method method = clazz.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(context, valueId);
                    field.setAccessible(true);
                    field.set(context, view);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    /**
     * 布局注入
     */
    private static void injectLayout(Object context) {

        int layouId = 0;

        Class<?> clazz = context.getClass();

        ContentView contentView = clazz.getAnnotation(ContentView.class);

        if (contentView != null) {
            layouId = contentView.value();
        }

        try {
            Method method = context.getClass().getMethod("setContentView", int.class);
            method.invoke(context, layouId);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}
