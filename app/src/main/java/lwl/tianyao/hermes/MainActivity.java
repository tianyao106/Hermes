package lwl.tianyao.hermes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.winfo.gdmsaec.app.hermes.core.Heremes;

import lwl.tianyao.hermes.manager.UserManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Heremes.regiest(UserManager.class);

        UserManager.getInstance().setUserName("主进程设置用户：lwl");
    }

    public void getUserName(View view) {
        Toast.makeText(this, UserManager.getInstance().getUserName(), Toast.LENGTH_SHORT).show();
    }

    public void setUserName(View view) {
        UserManager.getInstance().setUserName("主进程设置用户：lwl");
    }

    public void goTest(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void go(View view) {
        Intent intent = new Intent(this, MMActivity.class);
        startActivity(intent);
    }

}
