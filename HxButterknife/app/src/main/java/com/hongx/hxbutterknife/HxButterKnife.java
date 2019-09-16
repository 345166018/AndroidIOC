package com.hongx.hxbutterknife;

import android.app.Activity;

/**
 * @author: fuchenming
 * @create: 2019-09-10 09:37
 */
public class HxButterKnife {

    public static void bind(Activity activity) {
//        String name = activity.getClass().getName() + "_ViewBinding";
        String name = activity.getClass().getName() + "_ViewBinding2";
        try {
            Class<?> aClass = Class.forName(name);
            IBinder iBinder = (IBinder) aClass.newInstance();
            iBinder.bind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
