package zcy.applibrary.http;

/**
 * Create 2019/7/2 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public enum ResponseCode {

    RC101(101,"服务器返回内容异常"),RC102(102,"找不到服务器"),RC103(103,"手机网络异常，请检查您的网络"),RC200(200,"成功！");

    private int code;
    private String value;

    ResponseCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
