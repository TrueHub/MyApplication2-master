package com.utang.vervel.net;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by user on 2017/4/13.
 * Conection的扩展类，主要用来处理数据返回为对象数组的操作类ArrayConnection
 */

public class ArrayConnection extends Connection {
    public ArrayConnection(String ip, int host) {
        super(ip, host);
    }

    @Override
    protected  <T> T getData(JSONObject jsonObject, Class<T> mClass) {
        return JSONArray.toJavaObject(jsonObject.getJSONArray("data"), mClass);
    }
}