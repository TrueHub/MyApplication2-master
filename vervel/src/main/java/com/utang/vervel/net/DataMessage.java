package com.utang.vervel.net;

import java.util.HashMap;

/**
 * Created by user on 2017/4/13.
 */

public class DataMessage {
    private int code ;

    private HashMap<String,Object> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
