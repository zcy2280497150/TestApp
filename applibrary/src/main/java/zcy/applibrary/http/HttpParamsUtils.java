package zcy.applibrary.http;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.model.HttpParams;

public class HttpParamsUtils {

    public static class Builder{

        HttpParams params;

        public Builder() {
            params = new HttpParams();
        }

        public Builder addParams(String key , Object value){
            params.put(key,value instanceof String ? (String) value : JSON.toJSONString(value));
            return this;
        }

        public HttpParams builder(){
            return params;
        }

    }

}
