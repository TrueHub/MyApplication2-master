package com.utang.vervel.net;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by user on 2017/4/13.
 * Conection的扩展类，主要用来处理数据返回为单一对象的操作类ObjectConnection
 */


public class ObjectConnection extends Connection {
    public ObjectConnection(String ip, int host) {
        super(ip, host);
    }

    @Override
    protected <T> T getData(JSONObject jsonObject, Class<T> mClass) {
        return JSONObject.toJavaObject(jsonObject.getJSONObject("data"), mClass);
    }

}
