package zcy.applibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;

import zcy.applibrary.activity.BaseActivity;
import zcy.applibrary.dialog.BaseDialog;
import zcy.applibrary.dialog.LoadingDialog;
import zcy.applibrary.utils.MyLog;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBottomUIMenu();
        setContentView(R.layout.activity_welcome);
        MyLog.i("app version name = " + APP.getVersionName());
        MyLog.i("app version code = " + APP.getVersionCode());
//        new BaseDialog().setHolder(new LoadingDialog(null).setGravity(Gravity.CENTER)).show(getSupportFragmentManager(),null);
        toMain();
    }

    private void toMain() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
