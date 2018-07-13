package com.winfo.gdmsaec.app.hermes.core.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Response implements Parcelable{

    //执行远程方法的返回结果
    private String source;

    //描述信息
    private String message;

    //是否成功执行远程方法
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(String source, String message, boolean isSuccess) {
        this.source = source;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    protected Response(Parcel in) {
        source = in.readString();
        message = in.readString();
        isSuccess = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(source);
        dest.writeString(message);
        dest.writeByte((byte) (isSuccess ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    @Override
    public String toString() {
        return "Response{" +
                "source='" + source + '\'' +
                ", message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
