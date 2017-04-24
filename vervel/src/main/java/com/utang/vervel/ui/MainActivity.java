package com.utang.vervel.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.utang.vervel.R;
import com.utang.vervel.beans.AngV;
import com.utang.vervel.beans.DeviceStatusBean;
import com.utang.vervel.beans.GravA;
import com.utang.vervel.beans.Mag;
import com.utang.vervel.beans.Pressure;
import com.utang.vervel.beans.Pulse;
import com.utang.vervel.beans.PulseBean;
import com.utang.vervel.beans.UserBean;
import com.utang.vervel.eventbean.EventNotification;
import com.utang.vervel.moudul.ControlDeviceImp;
import com.utang.vervel.service.GATTService;
import com.utang.vervel.service.WriteService;
import com.utang.vervel.utils.DateUtils;
import com.utang.vervel.utils.EventUtil;
import com.utang.vervel.utils.RequestPermissionUtils;
import com.utang.vervel.utils.ServiceUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.utang.vervel.R.id.btn_search_pulse;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_connect;
    private Button btn_search_device;
    private Button btn_search_time;
    private Button btn_search_pulse_his;
    private Button btn_search_AOG;
    private Button btn_search_palstance;
    private Button btn_search_magnetism;
    private Button btn_search_pressure;
    private TextView tv_device_status;
    private TextView tv_phone_time;
    private TextView tv_device_time;
    private TextView tv_pulse;
    private TextView tv_AOG_X;
    private TextView tv_AOG_Y;
    private TextView tv_AOG_Z;
    private TextView tv_palstance_X;
    private TextView tv_palstance_Y;
    private TextView tv_palstance_Z;
    private TextView tv_pressure;
    private TextView tv_device_name;
    private Switch sw_pulse_upload;

    private ControlDeviceImp controlDeviceImp;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case 0://手机上的时间
                    tv_phone_time.setText(msg.getData().getString("time"));
                    break;
                case 1://device时间
                    tv_device_time.setText(DateUtils.getDateToString(msg.getData().getInt("time") * 1000));
            }
            return false;
        }
    });
    private Runnable runnable_time;
    private TextView tv_getdata_his_time;
    private TextView tv_pulse_his;
    private TextView tv_magnetism_X;
    private TextView tv_magnetism_Y;
    private TextView tv_magnetism_Z;
    private Toast toast;
    private boolean isStarted;
    ArrayList<Pulse> pulseArrayList = new ArrayList<>();
    ArrayList<Mag> magArrayList = new ArrayList<>();
    ArrayList<Pressure> pressureArrayList = new ArrayList<>();
    ArrayList<GravA> gravAArrayList = new ArrayList<>();
    ArrayList<AngV> angVArrayList = new ArrayList<>();
    private UserBean userBean;
    private Intent writeServiceIntent;
    private TextView tv_trust_lv;
    private TextView tv_trust_lv_his;
    private Button btn_delete_flash;
    private Intent gattService;
    private int LIST_SIZE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        requestMyPermissions();

        EventUtil.register(this);

        if (userBean == null) {
            userBean = UserBean.getInstence();
        }

        toast = Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT);

        controlDeviceImp = new ControlDeviceImp(this);

        runnable_time = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("time", DateUtils.getCurrentDate());
                msg.setData(bundle);
                handler.sendMessage(msg);
                handler.postDelayed(runnable_time, 0);
            }
        };
        new Thread(runnable_time).start();
    }

    private void requestMyPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            RequestPermissionUtils.requestPermission(this, permissions, "BLE设备连接蓝牙还需要获取以下权限");
        }
    }

    private void initView() {
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_search_device = (Button) findViewById(R.id.btn_search_device);
        tv_device_status = (TextView) findViewById(R.id.tv_device_status);
        btn_search_time = (Button) findViewById(R.id.btn_search_his);
        tv_phone_time = (TextView) findViewById(R.id.tv_phone_time);
        tv_device_time = (TextView) findViewById(R.id.tv_device_time);
        tv_pulse = (TextView) findViewById(R.id.tv_pulse);
        btn_search_pulse_his = (Button) findViewById(R.id.btn_search_pulse_his);
        btn_search_AOG = (Button) findViewById(R.id.btn_search_AOG);
        tv_AOG_X = (TextView) findViewById(R.id.tv_AOG_X);
        tv_AOG_Y = (TextView) findViewById(R.id.tv_AOG_Y);
        tv_AOG_Z = (TextView) findViewById(R.id.tv_AOG_Z);
        btn_search_palstance = (Button) findViewById(R.id.btn_search_palstance);
        tv_palstance_X = (TextView) findViewById(R.id.tv_palstance_X);
        tv_palstance_Y = (TextView) findViewById(R.id.tv_palstance_Y);
        tv_palstance_Z = (TextView) findViewById(R.id.tv_palstance_Z);
        btn_search_magnetism = (Button) findViewById(R.id.btn_search_magnetism);
        btn_search_pressure = (Button) findViewById(R.id.btn_search_pressure);
        tv_pressure = (TextView) findViewById(R.id.tv_pressure);
        btn_connect.setOnClickListener(this);
        btn_search_device.setOnClickListener(this);
        btn_search_time.setOnClickListener(this);
        btn_search_pulse_his.setOnClickListener(this);
        btn_search_AOG.setOnClickListener(this);
        btn_search_palstance.setOnClickListener(this);
        btn_search_magnetism.setOnClickListener(this);
        btn_search_pressure.setOnClickListener(this);
        tv_getdata_his_time = (TextView) findViewById(R.id.tv_getdata_his_time);
        tv_pulse_his = (TextView) findViewById(R.id.tv_pulse_his);
        tv_magnetism_X = (TextView) findViewById(R.id.tv_magnetism_X);
        tv_magnetism_Y = (TextView) findViewById(R.id.tv_magnetism_Y);
        tv_magnetism_Z = (TextView) findViewById(R.id.tv_magnetism_Z);
        tv_trust_lv = (TextView) findViewById(R.id.tv_trust_lv);
        tv_trust_lv_his = (TextView) findViewById(R.id.tv_trust_lv_his);

        btn_search_pulse_his.setClickable(false);
        btn_search_AOG.setClickable(false);
        btn_search_palstance.setClickable(false);
        btn_search_magnetism.setClickable(false);
        btn_search_pressure.setClickable(false);

        btn_delete_flash = (Button) findViewById(R.id.btn_delete_flash);
        btn_delete_flash.setOnClickListener(this);
        tv_device_name = (TextView) findViewById(R.id.tv_device_name);
        tv_device_name.setOnClickListener(this);
        sw_pulse_upload = (Switch) findViewById(R.id.sw_pulse_upload);
        sw_pulse_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("MSL", "onCheckedChanged: ");
                if (isChecked) {
                    EventUtil.post("PULSE_UP_ON");
                }else{
                    EventUtil.post("PULSE_UP_OFF");
                    tv_trust_lv.setText("--");
                    tv_pulse.setText("--次/分钟");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect:
                requestMyPermissions();
                if (btn_connect.getText().equals("连接")) {
                    if (ServiceUtils.isServiceWork(this, getString(R.string.gattServiceName))) {
                        EventUtil.post("START CONNECT");
                        Log.e("MSL", "onClick: gatt service is running");
                    } else {
                        gattService = new Intent(this, GATTService.class);
                        startService(gattService);
                        writeServiceIntent = new Intent(this, WriteService.class);
                        startService(writeServiceIntent);
                        Log.e("MSL", "onClick: gatt is not running");
                    }
                } else if (btn_connect.getText().equals("断开")) {
                    EventUtil.post("STOP GATT_SERVICE");
                    btn_search_pulse_his.setClickable(false);
                    btn_search_AOG.setClickable(false);
                    btn_search_palstance.setClickable(false);
                    btn_search_magnetism.setClickable(false);
                    btn_search_pressure.setClickable(false);
                    btn_delete_flash.setClickable(false);
                }

                break;
            case R.id.btn_search_device:
                EventUtil.post("SEARCH_DEVICE_STATUE");
                break;
            case R.id.btn_search_his:

                EventUtil.post("SEARCH_HIS");

                btn_search_pulse_his.setClickable(true);
                btn_search_AOG.setClickable(true);
                btn_search_palstance.setClickable(true);
                btn_search_magnetism.setClickable(true);
                btn_search_pressure.setClickable(true);

                break;
            case R.id.btn_search_pulse_his:
                controlDeviceImp.searchPulseHis(pulseArrayList);
                break;
            case R.id.btn_search_AOG:
                controlDeviceImp.searchAOG(gravAArrayList);
                break;
            case R.id.btn_search_palstance:
                controlDeviceImp.searchPalstance(angVArrayList);
                break;
            case R.id.btn_search_magnetism:
                controlDeviceImp.searchMagnetism(magArrayList);
                break;
            case R.id.btn_search_pressure:
                controlDeviceImp.searchPressure(pressureArrayList);
                break;
            case R.id.btn_delete_flash:
                showMessageOKCancel(this, "确定要清空蓝牙设备里量测好的数据吗？（会丢失部分数据） 请确认已经接受完所有数据！！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EventUtil.post("DELETE FLASH");
                    }
                });
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (toast.getView().getParent() == null) {
            toast.show();

        } else {
            toast.cancel();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isStarted) {//停止Service
            stopService(writeServiceIntent);
            stopService(gattService);
            isStarted = false;
        }
        EventUtil.unregister(this);//注销eventbus
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGATTCallback(String str) {
        controlDeviceImp.showToast(str);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataOver(EventNotification eventNotification) {
        if (eventNotification.isGetOver()) {
            controlDeviceImp.showToast("连接" + eventNotification.getType() + "成功");
            tv_device_name.setVisibility(View.VISIBLE);
            tv_device_name.setText(eventNotification.getType());
            btn_connect.setText("断开");
        } else {
            initAll();
        }
    }

    /**
     * 断开连接后，activity所有控件恢复初始状态
     */
    private void initAll() {
        tv_device_name.setVisibility(View.GONE);
        btn_connect.setText("连接");
        tv_device_status.setText("---请先获取设备状态---");

        sw_pulse_upload.setChecked(false);

        tv_device_time.setText("----年-月-日-时-分-秒");

        tv_pulse.setText("--次/分钟");

        tv_getdata_his_time.setText("----年-月-日-时-分-秒");
        tv_pulse_his.setText("--次/分钟");

        tv_magnetism_X.setText("--");
        tv_magnetism_Y.setText("--");
        tv_magnetism_Z.setText("--");

        tv_palstance_X.setText("--");
        tv_palstance_Y.setText("--");
        tv_palstance_Z.setText("--");

        tv_AOG_X.setText("--");
        tv_AOG_Y.setText("--");
        tv_AOG_Z.setText("--");

        tv_pressure.setText("----");

        tv_trust_lv_his.setText("--");
        tv_trust_lv.setText("--");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGATTCallback(PulseBean pulseBean) {
        tv_pulse.setText(pulseBean.getPulse() + "次/分钟");
        switch (pulseBean.getTrustLevel()) {
            case 0:
                tv_trust_lv.setText("无");
                break;
            case 1:
                tv_trust_lv.setText("低");

                break;
            case 2:
                tv_trust_lv.setText("中");

                break;
            case 3:
                tv_trust_lv.setText("高");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void getGATTCallback(DeviceStatusBean deviceStatusBean) {

        final int[] time = {deviceStatusBean.getTime()};
//        Log.i("MSL", "getBluetoothHelathCallback: " + (long) time[0] * 1000 + "," + time[0]);
        int pulseAbnomal_min = deviceStatusBean.getPulseAbnomal_min();
        int pulseAbnomal_max = deviceStatusBean.getPulseAbnomal_max();
        int simplingFreq = deviceStatusBean.getSimplingFreq();
        final int deviceElec = deviceStatusBean.getDeviceElec();

        tv_device_time.setText(DateUtils.getDateToString((long) time[0] * 1000));
        tv_device_status.setText("心率振动阀值:" + pulseAbnomal_min + "|" + pulseAbnomal_max
                + "次/分钟\n九轴传感器采样频率:  " + simplingFreq
                + "Hz\n剩余电量:  " + deviceElec + "%");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void getGATTCallback(Pulse pulse) {
        //心率历史
        pulseArrayList.add(pulse);
        if (pulseArrayList.size() == LIST_SIZE) {
            ArrayList<Pulse> list = new ArrayList<>();
            list.addAll(pulseArrayList);
            userBean.setPulseArrayList(list);
            pulseArrayList.clear();
//            Log.i("MSL", "getBluetoothHelathCallback: " + userBean.getPulseArrayList().size() + "," + pulseArrayList.size() + "," +list.size());
        }
        tv_getdata_his_time.setText(DateUtils.getDateToString(pulse.getTime() * 1000));
        tv_pulse_his.setText(String.valueOf(pulse.getPulse()) + "次/分钟");

        switch (pulse.getTrustLevel()) {
            case 0:
                tv_trust_lv_his.setText("无");
                break;
            case 1:
                tv_trust_lv_his.setText("低");

                break;
            case 2:
                tv_trust_lv_his.setText("中");

                break;
            case 3:
                tv_trust_lv_his.setText("高");
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void getGATTCallback(GravA gravA) {
        //心率历史
        gravAArrayList.add(gravA);
        if (gravAArrayList.size() == LIST_SIZE) {
            ArrayList<GravA> list = new ArrayList<>();
            list.addAll(gravAArrayList);
            userBean.setGravAArrayList(list);
            gravAArrayList.clear();
//            Log.i("MSL", "getBluetoothHelathCallback: " + userBean.getGravAArrayList().size() + "," + gravAArrayList.size() + "," +list.size());
        }
        tv_getdata_his_time.setText(DateUtils.getDateToString(gravA.getTime() * 1000));
        tv_AOG_X.setText(String.valueOf(gravA.getVelX()));
        tv_AOG_Y.setText(String.valueOf(gravA.getVelY()));
        tv_AOG_Z.setText(String.valueOf(gravA.getVelZ()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void getGATTCallback(AngV angV) {
        //心率历史
        angVArrayList.add(angV);
        if (angVArrayList.size() == LIST_SIZE) {
            ArrayList<AngV> list = new ArrayList<>();
            list.addAll(angVArrayList);
            userBean.setAngVArrayList(list);
            angVArrayList.clear();
//            Log.i("MSL", "getBluetoothHelathCallback: " + userBean.getAngVArrayList().size() + "," + angVArrayList.size() + "," +list.size());
        }
        tv_palstance_X.setText(String.valueOf(angV.getVelX()));
        tv_palstance_Y.setText(String.valueOf(angV.getVelY()));
        tv_palstance_Z.setText(String.valueOf(angV.getVelZ()));
        tv_getdata_his_time.setText(DateUtils.getDateToString(angV.getTime() * 1000));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void getGATTCallback(Mag mag) {
        //心率历史
        magArrayList.add(mag);
        if (magArrayList.size() == LIST_SIZE) {
            ArrayList<Mag> list = new ArrayList<>();
            list.addAll(magArrayList);
            userBean.setMagArrayList(list);
            magArrayList.clear();
//            Log.i("MSL", "getBluetoothHelathCallback: " + userBean.getPulseArrayList().size() + "," + magArrayList.size() + "," +list.size());
        }
        tv_magnetism_X.setText(String.valueOf(mag.getStrengthX()));
        tv_magnetism_Y.setText(String.valueOf(mag.getStrengthY()));
        tv_magnetism_Z.setText(String.valueOf(mag.getStrengthZ()));
        tv_getdata_his_time.setText(DateUtils.getDateToString(mag.getTime() * 1000));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void getGATTCallback(Pressure pressure) {
        //心率历史
        pressureArrayList.add(pressure);
        if (pressureArrayList.size() == LIST_SIZE) {
            ArrayList<Pressure> list = new ArrayList<>();
            list.addAll(pressureArrayList);
            userBean.setPressureArrayList(list);
            pressureArrayList.clear();
//            Log.i("MSL", "getBluetoothHelathCallback: " + userBean.getPulseArrayList().size() + "," + pressureArrayList.size() + "," +list.size());
        }
//        Log.i("MSL", "getBluetoothHelathCallback: pressure.getTime() * 1000 = " + pressure.getTime() * 1000);
        tv_pressure.setText(String.valueOf(pressure.getIntensityOfPressure()));
        tv_getdata_his_time.setText(DateUtils.getDateToString(pressure.getTime() * 1000));
    }


    private static void showMessageOKCancel(Activity activity, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("已经接收完了", okListener)
                .setNegativeButton("暂时不删", null)
                .create()
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
