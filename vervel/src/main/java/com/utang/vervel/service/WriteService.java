package com.utang.vervel.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.utang.vervel.beans.UserBean;
import com.utang.vervel.dbUtils.DataBaseContext;
import com.utang.vervel.dbUtils.SqliteHelper;
import com.utang.vervel.utils.WriteToCSV;

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

        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    //存储为sqlite3文件
//                    writeBySqlite3();

//                    存储为本地csv文件,两种存储方式不能同时处理(list.clear()的原因)
                    writeByCsv();
                }
            }
        }.start();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void writeByCsv() {
        if (userBean.getAogArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writeAOG(userBean.getAogArrayList(), "AOG.csv");
        }
        if (userBean.getMagnetismArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writeMagnetism(userBean.getMagnetismArrayList(), "Magnetism.csv");
        }
        if (userBean.getPalstanceArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writePalstance(userBean.getPalstanceArrayList(), "palstance.csv");
        }
        if (userBean.getPressureArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writePressure(userBean.getPressureArrayList(), "Pressure.csv");
        }
        if (userBean.getPulseArrayList().size() >= DATA_SIZE) {
            WriteToCSV.writePulse(userBean.getPulseArrayList(), "Pulse.csv");
        }
    }

    public void writeBySqlite3() {
        if (userBean.getAogArrayList().size() >= DATA_SIZE
                || userBean.getMagnetismArrayList().size() >= DATA_SIZE
                || userBean.getPalstanceArrayList().size() >= DATA_SIZE
                || userBean.getPressureArrayList().size() >= DATA_SIZE
                || userBean.getPulseArrayList().size() >= DATA_SIZE
                ) {
            SqliteHelper sqliteHelper = new SqliteHelper(new DataBaseContext(getApplicationContext()));//存在SD卡中
//            SqliteHelper sqliteHelper = new SqliteHelper(getApplicationContext());//存在内存中
            sqliteHelper.insertDataBySw();
        }
    }

}
