package com.utang.vervel.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.utang.vervel.beans.UserBean;
import com.utang.vervel.dbUtils.DataBaseContext;
import com.utang.vervel.dbUtils.SqliteHelper;
import com.utang.vervel.utils.ConstantPool;
import com.utang.vervel.utils.WriteToCSV;

/**
 * 将数据写入本地 (sqlite3 & csv) 的service
 */
public class WriteService extends Service {
    private String url;

    public WriteService() {
    }

    private WriteToCSV writeToCSV ;
    private UserBean userBean;
    private final int DATA_SIZE = 1;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userBean = UserBean.getInstence();
//        url = ConstantPool.URL_DEBUG_LAN;
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
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String net = intent.getStringExtra("net");
        Log.d("MSL", "onStartCommand: 当前net：" + net);
        if (net.equals("mobile")) {
            url = ConstantPool.URL_DEBUG_WLAN;
        }else if (net.equals("wifi")){
            if (TextUtils.isEmpty(intent.getStringExtra("wifiName")) || TextUtils.isEmpty(intent.getStringExtra("wifiMac"))){
                url = ConstantPool.URL_DEBUG_WLAN;
            }
            Log.d("MSL", "onStartCommand: " + intent.getStringExtra("wifiName") + "," + intent.getStringExtra("wifiMac"));
            String wifiName = intent.getStringExtra("wifiName");
            String wifiMac = intent.getStringExtra("wifiMac");
            Log.i("MSL", "onStartCommand: " + wifiName + "," + wifiMac);
            if (!wifiName.equals(ConstantPool.debugWifiName) || !wifiMac.equals(ConstantPool.debugWifiMac))
                url = ConstantPool.URL_DEBUG_WLAN;
            else
                url = ConstantPool.URL_DEBUG_LAN;
        }
        writeToCSV = new WriteToCSV(url);
        return super.onStartCommand(intent, flags, startId);
    }

    public void writeByCsv() {


        if (userBean.getGravAArrayList().size() >= DATA_SIZE) {
            writeToCSV.writeGravA(userBean.getGravAArrayList(), "GravA.csv");
        }
        if (userBean.getMagArrayList().size() >= DATA_SIZE) {
            writeToCSV.writeMag(userBean.getMagArrayList(), "Mag.csv");
        }
        if (userBean.getAngVArrayList().size() >= DATA_SIZE) {
            writeToCSV.writeAngV(userBean.getAngVArrayList(), "AngV.csv");
        }
        if (userBean.getPressureArrayList().size() >= DATA_SIZE) {
            writeToCSV.writePressure(userBean.getPressureArrayList(), "Pressure.csv");
        }
        if (userBean.getPulseArrayList().size() >= DATA_SIZE) {
            writeToCSV.writePulse(userBean.getPulseArrayList(), "Pulse.csv");
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
