package lwl.tianyao.hermes;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import lwl.tianyao.hermes.manager.MainService;

public class TestActivity extends Activity {

    private ServiceConnection serviceConnection;

    IMainService iMainService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMainService = IMainService.Stub.asInterface(service);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        bindService(new Intent(this, MainService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    public void getUserName(View view) {
        try {
            Toast.makeText(this, iMainService.getUserName(), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setUserName(View view) {
        try {
            iMainService.setUserName("test进程设置用户：lwl");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void go(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
