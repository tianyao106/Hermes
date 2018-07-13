package lwl.tianyao.hermes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.winfo.gdmsaec.app.hermes.core.Heremes;

import lwl.tianyao.hermes.manager.IUserManager;

public class MMActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);
        Heremes.connect(this);
    }

    public void getUserName(View view) {
        IUserManager iUserManager = Heremes.getInstance(IUserManager.class);
        Toast.makeText(this, iUserManager.getUserName(), Toast.LENGTH_SHORT).show();
    }

    public void setUserName(View view) {
        IUserManager iUserManager = Heremes.getInstance(IUserManager.class);
        iUserManager.setUserName("----MM进程设置用户：lwl");
    }

    public void go(View view) {
        finish();
    }

}
