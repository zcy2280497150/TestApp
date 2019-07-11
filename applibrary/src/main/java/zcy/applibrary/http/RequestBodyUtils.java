package zcy.applibrary.http;

import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyUtils {

    private static final String MEDIA_TYPE = "application/text; charset=utf-8";

    public static class Builder{

        private JSONObject params;

        Builder() {
            params = new JSONObject();
        }

        public Builder addParam(String key , Object value){
            params.put(key,value);
            return this;
        }

        public RequestBody builder(){
            return RequestBody.create(MediaType.parse(MEDIA_TYPE),null == params ? "" : params.toString());
        }

    }

}
