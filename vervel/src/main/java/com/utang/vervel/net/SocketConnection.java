package com.utang.vervel.net;



import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/**
 * Created by user on 2017/4/13.
 * 网络传输核心类SocketConnection（主要用来封装最基本的数据传输类）
 */
public class SocketConnection {
    private final static String TAG = "SocketConnection";
    private final static String END = "#end#";//用来结束发送的字符串
    private Socket client = null;
    private BufferedReader ois = null;
    private PrintStream oos = null;

    public SocketConnection(String ip, int host) throws IOException {
        client = new Socket(ip, host);
        oos = new PrintStream(client.getOutputStream());
        ois = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public String requestData(DataMessage messageOut) throws IOException {
        String result;
        String json = JSON.toJSONString(messageOut);
        sendString(oos, json);
        result = readString(ois);
        Log.i(TAG, result);
        oos.close();
        ois.close();
        if (!client.isClosed()) {
            client.close();
        }
        return result;
    }
    /**
     *用来读取输入流的内容
     */
    private String readString(BufferedReader ois) throws IOException {
        String result;
        String bufferText;
        StringBuffer resultBuffer = new StringBuffer();
        while (!(bufferText = ois.readLine()).equals(END)) {
            resultBuffer.append(bufferText);
        }
        result = resultBuffer.toString();
        return result;
    }

    /**
     *用来写入数据到输出流
     */
    private void sendString(PrintStream out, String text) {
        out.println(text);
        out.println(END);
        out.flush();
    }
}