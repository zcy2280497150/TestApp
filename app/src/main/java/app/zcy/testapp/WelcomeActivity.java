package app.zcy.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;

import java.util.List;

import zcy.applibrary.activity.BaseActivity;
import zcy.applibrary.dialog.BaseDialog;
import zcy.applibrary.dialog.LoadingDialog;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBottomUIMenu();
        setContentView(R.layout.activity_welcome);
        setStatusBarTxtBlack(true);
//        new BaseDialog().setHolder(new LoadingDialog(null).setGravity(Gravity.CENTER)).show(getSupportFragmentManager(),null);
        toMain();
    }

    private void toMain() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
