package com.winfo.gdmsaec.app.hermes.core;

import android.content.Context;
import android.util.Log;

import com.winfo.gdmsaec.app.hermes.core.model.Request;
import com.winfo.gdmsaec.app.hermes.core.model.Response;

import java.lang.reflect.Proxy;

public class Heremes {

    private static final String TAG = "Heremes";

    //服务端
    //注册 允许客户端调用的接口
    public static void regiest(Class<?> service) {
        TypeCenter.getInstance().regiest(service);
    }

    //客户端
    public static void connect(Context context) {
        connect(context, null, HermesService.HermesService0.class);
    }

    public static void connect(Context context, Class<? extends HermesService> service) {
        connect(context, null, service);
    }

    public static void connect(Context context, String pkgName) {
        connect(context, pkgName, HermesService.HermesService0.class);
    }

    public static void connect(Context context, String pkgName, Class<? extends HermesService> service) {
        Channel.getInstance().bind(context, pkgName, service);
    }

    public static void disconnect(Context context, Class<? extends HermesService> service) {
        Channel.getInstance().unBind(context.getApplicationContext(), service);
    }

    public static <T> T getInstance(Class<T> instanceClass, Object... parameters) {
        return getInstance(instanceClass, HermesService.HermesService0.class, parameters);
    }

    public static <T> T getInstance(Class<T> instanceClass, Class<? extends HermesService> service, Object... parameters) {
        return getInstance(instanceClass, "getInstance", service, parameters);
    }

    public static <T> T getInstance(Class<T> instanceClass, String methodName, Class<? extends HermesService> service, Object... parameters) {
        Response response = Channel.getInstance().send(Request.GET_INSTANCE, service, instanceClass, methodName, parameters);
        if (response.isSuccess()) {
            return getProxy(instanceClass, service);
        }
        Log.e(TAG, response.getMessage());
        return null;
    }

    static <T> T getProxy(Class<T> instanceClass, Class<? extends HermesService> service) {
        return (T) Proxy.newProxyInstance(instanceClass.getClassLoader(), new Class[]{instanceClass}, new HermesInvocationHandler(instanceClass, service));
    }

}
