package zcy.applibrary.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import java.io.IOException;
import okhttp3.ResponseBody;
import zcy.applibrary.APP;
import zcy.applibrary.dialog.BaseDialog;
import zcy.applibrary.dialog.LoadingDialog;
import zcy.applibrary.utils.MyLog;

public abstract class HttpSimpleCallback extends StringCallback {

    public abstract void onSuccess(long code, String data);

    private void onSuccess(ResponseCode responseCode,String data){
        onSuccess(responseCode.getCode(),responseCode == ResponseCode.RC200 ? data : responseCode.getValue());
        hideDialog();
    }

    private void onSuccess(ResponseCode responseCode){
        onSuccess(responseCode,null);
    }

    private BaseDialog dialog;
    private FragmentManager fm;

    public HttpSimpleCallback() {
        this(null);
    }

    public HttpSimpleCallback( FragmentManager fm ) {
        this(fm,null);
    }

    public HttpSimpleCallback(FragmentManager fm , String text) {
        this.fm = fm;
        if ( null != fm) {
            dialog = new BaseDialog().setHolder(new LoadingDialog(text).setGravity(Gravity.CENTER));
        }
    }

    HttpSimpleCallback showDialog() {
        if (null != dialog && null != fm) {
            dialog.show(fm, null);
        }
        return this;
    }

    private void hideDialog() {
        if (null != dialog && null != fm) {
            dialog.dismiss();
        }
    }

    @Override
    public void onSuccess(Response<String> response) {
        hideDialog();
        String body = response.body();
        // TODO: 2018/8/30 异常压制
        if (TextUtils.isEmpty(body) || !body.startsWith("{") || !body.endsWith("}")) {
            onSuccess(ResponseCode.RC101);
        } else {
            onSuccess(ResponseCode.RC200,body);
        }
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        if (isNetworkConnected()) {
            if (404 == response.code()) {
                onSuccess(ResponseCode.RC102);
            } else {
                try {
                    okhttp3.Response rawResponse = response.getRawResponse();
                    if (null != rawResponse) {
                        ResponseBody body = rawResponse.body();
                        if (null != body){
                            response.setBody(body.string());
                        }
                    }
                    onSuccess(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    onSuccess(ResponseCode.RC101);
                }
            }
        } else {
            onSuccess(ResponseCode.RC103);
        }
    }

    /**
     * 检测网络是否可用
     * @return 是否可用
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) APP.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = null == cm ? null : cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

}
