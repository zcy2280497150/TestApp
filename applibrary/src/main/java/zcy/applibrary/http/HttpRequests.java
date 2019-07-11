package zcy.applibrary.http;

import android.support.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import okhttp3.RequestBody;

/**
 * OkGo网路请求框架封装
 * zcy 2018/6/25
 */
public class HttpRequests {

    /**
     * POST请求封装
     * @param url         url
     * @param headers     请求头
     * @param requestBody 请求体
     */
    protected static void requestPOST(String url, HttpHeaders headers, RequestBody requestBody, HttpSimpleCallback callback) {
        OkGo.<String>post(url).headers(headers).upRequestBody(requestBody).execute(callback.showDialog());
    }

    /**
     * POST请求封装
     *
     * @param url         url
     * @param headers     请求头
     */
    protected static void requestPOST(String url, HttpHeaders headers, HttpParams params, HttpSimpleCallback callback) {
        OkGo.<String>post(url).headers(headers).params(params).execute(callback.showDialog());
    }

    /**
     * GET请求封装
     * @param url     url
     * @param headers 请求头
     * @param params  请求体
     */
    protected static void requestGET(String url, HttpHeaders headers, HttpParams params, @NonNull HttpSimpleCallback callback) {
        OkGo.<String>get(url).headers(headers).params(params).execute(callback.showDialog());
    }

}
