// Copyright 2019 ADTIMING TECHNOLOGY COMPANY LIMITED
// Licensed under the GNU Lesser General Public License Version 3

package com.android.adtdemo;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 */
public class AdLog {
    private static String clzName;

    private static ArrayList<String> methods;

    static {
        clzName = AdLog.class.getName();
        methods = new ArrayList<>();
        Method[] ms = AdLog.class.getDeclaredMethods();
        for (Method m : ms) {
            methods.add(m.getName());
        }
    }

    private static final String TAG = "AdLog";

    public static void LogE(String info) {
        Log.e(TAG, getMsgWithLineNumber(info));
    }

    /**
     * 获取带行号的日志信息内容。
     *
     * @param msg 日志内容。
     * @return 带行号的日志信息内容。
     */
    private static String getMsgWithLineNumber(String msg) {
        try {
            for (StackTraceElement st : (new Throwable()).getStackTrace()) {
                String name = st.getClassName();
                if (!clzName.equals(name) && !methods.contains(st.getMethodName())) {
                    return name.substring(name.lastIndexOf(".") + 1) + ": line " + st.getLineNumber() + "->" + st.getMethodName() + "(): " + msg;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}
