package com.utang.vervel.utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.utang.vervel.beans.AngV;
import com.utang.vervel.beans.GravA;
import com.utang.vervel.beans.Mag;
import com.utang.vervel.beans.Pressure;
import com.utang.vervel.beans.Pulse;
import com.utang.vervel.beans.UserBean;
import com.utang.vervel.beans.UserJsonBean;
import com.utang.vervel.eventbean.EventNotification;
import com.utang.vervel.net.RetrofitItfc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2017/4/12.
 */

public class WriteToCSV {

    private static UserBean userBean = UserBean.getInstence();


    //和服务器交互：将数据以json的形式传到服务器中
    private static void sendToService(String userJson){
        Log.i("MSL", "writeToServer: " + userJson);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantPool.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitItfc retrofitItfc = retrofit.create(RetrofitItfc.class);

        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), userJson);

        Call<ResponseBody> call = retrofitItfc.postUser(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.i("MSL", "onResponse: OK" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                EventUtil.post("上传成功");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("MSL", "onResponse: Fail" + t);
                EventUtil.post("上传失败");
            }
        });
    }

    public static void writeGravA(ArrayList<GravA> aoglist, String name) {
        ArrayList<GravA> list = new ArrayList<>();
        list.addAll(aoglist);
        userBean.getGravAArrayList().clear();

        UserJsonBean userJsonBean = new UserJsonBean(ConstantPool.DEVICEID);
        userJsonBean.setGravAArrayList(list);
        String userJson = new Gson().toJson(userJsonBean);

        sendToService(userJson);

        if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {//如果不存在SD卡,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
        }
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡根目录
        fileDir += "/vervel/csv";
        File dirFile = new File(fileDir);
//        Log.e("MSL", "writeGravA: dirFile.exists() = " + dirFile.exists());
        if (!dirFile.exists()) {
           boolean iss =  dirFile.mkdirs();
//            Log.e("MSL", "writeGravA: filepath not exists,but i creat it :"  + iss);
        }

        File aogFile = new File(fileDir + "/" + name);
//        Log.e("MSL", "writeGravA: aogFile.exists() = " + aogFile.exists());
        if (!aogFile.exists()) {
            try {
                boolean creatResult = aogFile.createNewFile();
                Log.e("MSL", "creat GravA File: " + creatResult);
                addToFileByFileWriter(aogFile.getAbsolutePath(), "time,x,y,z\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String time;
            int x, y, z;
            time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
            x = list.get(i).getVelX();
            y = list.get(i).getVelY();
            z = list.get(i).getVelZ();
            buffer.append(time).append(",").append(x).append(",").append(y).append(",").append(z).append("\n");
        }
        addToFileByFileWriter(aogFile.getAbsolutePath(), buffer.toString());
    }

    public static void writeAngV(ArrayList<AngV> palstancelist, String name) {
        ArrayList<AngV> list = new ArrayList<>();
        list.addAll(palstancelist);
        userBean.getAngVArrayList().clear();

        UserJsonBean userJsonBean = new UserJsonBean(ConstantPool.DEVICEID);
        userJsonBean.setAngVArrayList(list);
        String userJson = new Gson().toJson(userJsonBean);

        sendToService(userJson);

        if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {//如果不存在SD卡,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
        }
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡根目录
        fileDir += "/vervel/csv";
        File dirFile = new File(fileDir);
        if (!dirFile.exists()) dirFile.mkdirs();

        File csvFile = new File(fileDir + "/" + name);

        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
                addToFileByFileWriter(csvFile.getAbsolutePath(), "time,x,y,z\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String time;
            int x, y, z;
            time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
            x = list.get(i).getVelX();
            y = list.get(i).getVelY();
            z = list.get(i).getVelZ();
            buffer.append(time).append(",").append(x).append(",").append(y).append(",").append(z).append("\n");
        }
        addToFileByFileWriter(csvFile.getAbsolutePath(), buffer.toString());
    }

    public static void writeMag(ArrayList<Mag> magList, String name) {
        ArrayList<Mag> list = new ArrayList<>();
        list.addAll(magList);
        userBean.getMagArrayList().clear();

        UserJsonBean userJsonBean = new UserJsonBean(ConstantPool.DEVICEID);
        userJsonBean.setMagArrayList(list);
        String userJson = new Gson().toJson(userJsonBean);

        sendToService(userJson);

        if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {//如果不存在SD卡,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
        }
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡根目录
        fileDir += "/vervel/csv";
        File dirFile = new File(fileDir);
        if (!dirFile.exists()) dirFile.mkdirs();

        File csvFile = new File(fileDir + "/" + name);

        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
                addToFileByFileWriter(csvFile.getAbsolutePath(), "time,x,y,z\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String time;
            int x, y, z;
            time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
            x = list.get(i).getStrengthX();
            y = list.get(i).getStrengthY();
            z = list.get(i).getStrengthZ();
            buffer.append(time).append(",").append(x).append(",").append(y).append(",").append(z).append("\n");
        }
        addToFileByFileWriter(csvFile.getAbsolutePath(), buffer.toString());
    }

    public static void writePressure(ArrayList<Pressure> PressureList, String name) {
        ArrayList<Pressure> list = new ArrayList<>();
        list.addAll(PressureList);
        userBean.getPressureArrayList().clear();

        UserJsonBean userJsonBean = new UserJsonBean(ConstantPool.DEVICEID);
        userJsonBean.setPressureArrayList(list);
        String userJson = new Gson().toJson(userJsonBean);

        sendToService(userJson);

        if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {//如果不存在SD卡,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
        }
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡根目录
        fileDir += "/vervel/csv";
        File dirFile = new File(fileDir);
        if (!dirFile.exists()) dirFile.mkdirs();

        File csvFile = new File(fileDir + "/" + name);

        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
                addToFileByFileWriter(csvFile.getAbsolutePath(), "time,pressure\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String time;
            long pressure;
            time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
            pressure = list.get(i).getIntensityOfPressure();
            buffer.append(time).append(",").append(pressure).append("\n");
        }
        addToFileByFileWriter(csvFile.getAbsolutePath(), buffer.toString());
    }

    public static void writePulse(ArrayList<Pulse> pulseArrayList, String name) {
        ArrayList<Pulse> list = new ArrayList<>();
        list.addAll(pulseArrayList);
        userBean.getPulseArrayList().clear();

        UserJsonBean userJsonBean = new UserJsonBean(ConstantPool.DEVICEID);
        userJsonBean.setPulseArrayList(list);
        String userJson = new Gson().toJson(userJsonBean);

        sendToService(userJson);

        if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {//如果不存在SD卡,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
        }
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡根目录
        fileDir += "/vervel/csv";
        File dirFile = new File(fileDir);
        if (!dirFile.exists()) dirFile.mkdirs();

        File csvFile = new File(fileDir + "/" + name);

        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
                addToFileByFileWriter(csvFile.getAbsolutePath(), "time,pulse,trustLevel\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String time;
            int pulse;
            int trustLevel;
            time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
            pulse = list.get(i).getPulse();
            trustLevel = list.get(i).getTrustLevel();
            buffer.append(time).append(",").append(pulse).append(",").append(trustLevel).append("\n");
        }
        addToFileByFileWriter(csvFile.getAbsolutePath(), buffer.toString());
    }

    //追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
    public static void addToFileByOutputStream(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 以追加形式写文件:写文件器，构造函数中的第二个参数为true
    public static void addToFileByFileWriter(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
