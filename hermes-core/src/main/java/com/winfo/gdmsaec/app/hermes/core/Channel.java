package com.winfo.gdmsaec.app.hermes.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.winfo.gdmsaec.app.hermes.core.anonotation.ClassId;
import com.winfo.gdmsaec.app.hermes.core.model.Parameters;
import com.winfo.gdmsaec.app.hermes.core.model.Request;
import com.winfo.gdmsaec.app.hermes.core.model.Response;

import java.util.concurrent.ConcurrentHashMap;

public class Channel {

    private static final String TAG = "xiaolu";

    private static volatile Channel instance;
    private ConcurrentHashMap<Class<? extends HermesService>, Boolean> mBound = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Class<? extends HermesService>, HermesServiceConnection> mHermesServices = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Class<? extends HermesService>, IHermesService> mIHermes = new ConcurrentHashMap<>();

    Gson gson = new Gson();

    private Channel() {
    }

    public static Channel getInstance() {
        if (null == instance) {
            synchronized (Channel.class) {
                if (null == instance) {
                    instance = new Channel();
                }
            }
        }
        return instance;
    }

    public void bind(Context context, String pkgName, Class<? extends HermesService> service) {
        //已经绑定了，就不再绑定
        Boolean isBound = mBound.get(service);
        if (null != isBound && isBound) {
            return;
        }

        Intent intent;
        if (TextUtils.isEmpty(pkgName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setClassName(pkgName, service.getName());
        }
        HermesServiceConnection hermesServiceConnection = new HermesServiceConnection(service);
        mHermesServices.put(service, hermesServiceConnection);
        context.bindService(intent, hermesServiceConnection, Context.BIND_AUTO_CREATE);

    }

    public void unBind(Context context, Class<? extends HermesService> service) {
        Boolean isBound = mBound.get(service);
        if (null == isBound || !isBound) {
            return;
        }

        HermesServiceConnection hermesServiceConnection = mHermesServices.remove(service);
        if (null != hermesServiceConnection) {
            context.unbindService(hermesServiceConnection);
        }
    }

    public Response send(int type, Class<? extends HermesService> service, Class<?> instanceClass, String methodName, Object... parameters) {
        Boolean isBound = mBound.get(service);
        if (null == isBound || !isBound) {
            return new Response(null, "服务未连接", false);
        }

        //获得classId
        ClassId annotation = instanceClass.getAnnotation(ClassId.class);
        String classId;
        if (null != annotation) {
            classId = annotation.value();
        } else {
            classId = instanceClass.getName();
        }
        Parameters[] parametersList = makeParameters(parameters);
        Request request = new Request(type, classId, methodName, parametersList);
        IHermesService iHermesService = mIHermes.get(service);
        try {
            if (iHermesService != null) {
                return iHermesService.send(request);
            } else {
                throw new RuntimeException();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Parameters[] makeParameters(Object[] parameters) {
        Parameters[] p;
        if (null != parameters) {
            p = new Parameters[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object object = parameters[i];
                p[i] = new Parameters(object.getClass().getName(), gson.toJson(object));
            }
        } else {
            p = new Parameters[0];
        }
        return p;
    }


    class HermesServiceConnection implements ServiceConnection {

        private final Class<? extends HermesService> mService;

        HermesServiceConnection(Class<? extends HermesService> service) {
            this.mService = service;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            IHermesService iHermesService = IHermesService.Stub.asInterface(iBinder);
            mIHermes.put(mService, iHermesService);
            mBound.put(mService, true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected: ");
            mIHermes.remove(mService);
            mBound.put(mService, false);
        }
    }
}
