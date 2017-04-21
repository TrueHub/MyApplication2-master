package com.utang.vervel.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.utang.vervel.beans.UserBean;
import com.utang.vervel.beans.UserJsonBean;
import com.utang.vervel.dbUtils.DataBaseContext;
import com.utang.vervel.dbUtils.SqliteHelper;
import com.utang.vervel.eventbean.EventNotification;
import com.utang.vervel.net.RetrofitItfc;
import com.utang.vervel.utils.EventUtil;
import com.utang.vervel.utils.WriteToCSV;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 将数据写入本地 (sqlite3 & csv) 的service
 */
public class WriteService extends Service {
    public WriteService() {
    }

    private UserBean userBean;
    private final int DATA_SIZE = 100;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userBean = UserBean.getInstence();
        Log.i("MSL", "onCreate: write service");
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    //存储为sqlite3文件
//                    writeBySqlite3();

//                    存储为本地csv文件,两种存储方式不能同时处理(list.clear()的原因)
                    writeByCsv();

//                    writeToServer();
                }
            }
        }.start();


    }

    private void writeToServer(ArrayList<Object> objList) {



    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void writeByCsv() {


        if (userBean.getGravAArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writeGravA(userBean.getGravAArrayList(), "GravA.csv");
        }
        if (userBean.getMagArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writeMag(userBean.getMagArrayList(), "Mag.csv");
        }
        if (userBean.getAngVArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writeAngV(userBean.getAngVArrayList(), "AngV.csv");
        }
        if (userBean.getPressureArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writePressure(userBean.getPressureArrayList(), "Pressure.csv");
        }
        if (userBean.getPulseArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writePulse(userBean.getPulseArrayList(), "Pulse.csv");
        }
    }

    public void writeBySqlite3() {
        if (userBean.getGravAArrayList().size() >= DATA_SIZE
                || userBean.getMagArrayList().size() >= DATA_SIZE
                || userBean.getAngVArrayList().size() >= DATA_SIZE
                || userBean.getPressureArrayList().size() >= DATA_SIZE
                || userBean.getPulseArrayList().size() >= DATA_SIZE
                ) {
            SqliteHelper sqliteHelper = new SqliteHelper(new DataBaseContext(getApplicationContext()));//存在SD卡中
//            SqliteHelper sqliteHelper = new SqliteHelper(getApplicationContext());//存在内存中
            sqliteHelper.insertDataBySw();
        }
    }

}
