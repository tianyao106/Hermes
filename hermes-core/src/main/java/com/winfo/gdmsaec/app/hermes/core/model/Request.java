package com.winfo.gdmsaec.app.hermes.core.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Request implements Parcelable{

    //获得单例对象
    public static final int GET_INSTANCE = 0;
    //执行方法
    public static final int GET_METHOD = 1;

    //请求类型
    private int type;
    //请求那个类（类的id）
    private String classType;
    //请求的方法名
    private String methodName;
    //执行方法的参数
    private Parameters[] parameters;


    public Request(int type, String classType, String methodName, Parameters[] parameters) {
        this.type = type;
        this.classType = classType;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    protected Request(Parcel in) {
        type = in.readInt();
        classType = in.readString();
        methodName = in.readString();
        parameters = in.createTypedArray(Parameters.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(classType);
        dest.writeString(methodName);
        dest.writeTypedArray(parameters, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Parameters[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameters[] parameters) {
        this.parameters = parameters;
    }
}
