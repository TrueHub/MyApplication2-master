package com.utang.vervel.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.utang.vervel.net.SocketConnection;

import java.io.IOException;

/**
 * Created by user on 2017/4/13.
 * 业务逻辑的数据传输类Connection
 * 主要用来对SocketConnection 进行进一步的封装处理，用来实现Android的网络线程异步处理，
 * 可以通过继承Connection类实现getData方法来做其他数据，如String格式的数据处理
 */

public abstract class Connection {
    private final static String TAG = "Connection";
    private String ip;
    private int host;

    public Connection(String ip, int host) {
        this.ip = ip;
        this.host = host;
    }

    /**
     * 发送数据给服务器端
     * @param requestData     请求的数据
     * @param resultClass     接收的数据类的类型
     * @param successListener 访问数据成功的回调函数
     * @param failListener    访问数据失败的回调函数，失败主要包含两种失败:
     *                        1.网络通信失败       2.服务器返回的ok值为false的失败
     *                        如果是网络通信失败     onFile会返回IOException
     *                        如果是ok为false       onFile会返回DataNullException
     * @param <T>             模板类
     */
    public <T> void sendRequest(final DataMessage requestData, Class<T> resultClass,
                                SuccessListener<T> successListener, FailListener failListener) {
        final MyHandler<T> handler = new MyHandler<T>(successListener, failListener, resultClass);
        new Thread() {
            @Override
            public void run() {
                boolean ok;
                String result;
                try {
                    result = new SocketConnection(ip, host).requestData(requestData);
                    ok = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    result = "";
                    ok = false;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("ok", ok);
                bundle.putString("data", result);
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }.start();
    }

    /**
     * 用来处理异步通讯
     */
    private class MyHandler<T> extends Handler {
        private SuccessListener<T> mSuccessListener;
        private FailListener mFailListener;
        private Class<T> mClass;

        public MyHandler(SuccessListener<T> successListener, FailListener failListener, Class<T> clazz) {
            super();
            this.mSuccessListener = successListener;
            this.mFailListener = failListener;
            this.mClass = clazz;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String result = data.getString("data");
            boolean ok = data.getBoolean("ok");
            Log.i(TAG, result);
            if (ok == true) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                boolean isOk = jsonObject.getBoolean("ok");
                if (isOk == true) {
                    T resultData = getData(jsonObject, mClass);
                    mSuccessListener.onResponse(resultData);
                } else {
                    String remindText = jsonObject.getString("data");
                    if (remindText == null) {
                        mFailListener.onFile(new DataNullException());
                    } else {
                        mFailListener.onFile(new DataNullException(remindText));
                    }
                }
            } else {
                mFailListener.onFile(new IOException());
            }
        }
    }

    /**
     * ok值为false抛出的提示异常，如果服务器端未传递值给data，则调用getMessage返回的值是null，如果服务器端有传递值给data，则调用getMessage返回的值为data对应的值
     */
    public class DataNullException extends Exception {
        private String message;

        public DataNullException() {
            super();
        }

        public DataNullException(String detailMessage) {
            super(detailMessage);
            message = detailMessage;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    /**
     * 该方法用来给继承实现，主要是为了去处理结果数据为数组或者为单一对象问题
     *
     * @param jsonObject 封装有数据的Json
     * @param mClass     返回结果的类型
     * @param <T>        结果的类型
     * @return 转化后的结果
     */
    protected abstract <T> T getData(JSONObject jsonObject, Class<T> mClass);

    public interface SuccessListener<T> {
        public void onResponse(T t);
    }

    public interface FailListener {
        public void onFile(Exception e);
    }
}