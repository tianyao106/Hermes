package com.winfo.gdmsaec.app.hermes.core;

import android.text.TextUtils;

import com.winfo.gdmsaec.app.hermes.core.anonotation.ClassId;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class TypeCenter {

    private static TypeCenter typeCenter = null;
    private ConcurrentHashMap<String, Class<?>> mClasses = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Method>> mMethods = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> mInstance = new ConcurrentHashMap<>();

    public static TypeCenter getInstance() {
        if (null == typeCenter) {
            synchronized (TypeCenter.class) {
                if (null == typeCenter) {
                    typeCenter = new TypeCenter();
                }
            }
        }
        return typeCenter;
    }

    /**
     * 记录对应class与其方法
     *
     * @param clazz
     */
    public void regiest(Class<?> clazz) {
        regiestClass(clazz);
        regiestMethod(clazz);
    }

    private void regiestMethod(Class<?> clazz) {
        mMethods.putIfAbsent(clazz, new ConcurrentHashMap<String, Method>());
        ConcurrentHashMap<String, Method> methods = mMethods.get(clazz);
        //应该将参数也加入key,因为可能重载
        for (Method method : clazz.getMethods()) {
            methods.put(method.getName(), method);
        }
    }

    private void regiestClass(Class<?> clazz) {
        ClassId classId = clazz.getAnnotation(ClassId.class);
        String className;
        if (null != classId) {
            className = classId.value();
        } else {
            className = clazz.getName();
        }
        mClasses.putIfAbsent(className, clazz);
    }

    public Class<?> getClassType(String classType) {
        if (TextUtils.isEmpty(classType)) {
            return null;
        }
        Class<?> clazz = mClasses.get(classType);
        if (clazz == null) {
            try {
                clazz = Class.forName(classType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

    public Method getMethod(Class<?> clazz, String methodName) {
        ConcurrentHashMap<String, Method> stringMethodConcurrentHashMap = mMethods.get(clazz);
        return stringMethodConcurrentHashMap.get(methodName);
    }

    public void putObject(String classType, Object instance) {
        mInstance.put(classType, instance);
    }

    public Object getObject(String classType) {
        return mInstance.get(classType);
    }
}
