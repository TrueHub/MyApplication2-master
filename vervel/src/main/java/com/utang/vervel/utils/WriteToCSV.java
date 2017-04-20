package com.utang.vervel.utils;

import android.os.Environment;
import android.util.Log;

import com.utang.vervel.beans.AOG;
import com.utang.vervel.beans.Magnetism;
import com.utang.vervel.beans.Palstance;
import com.utang.vervel.beans.Pressure;
import com.utang.vervel.beans.Pulse;
import com.utang.vervel.beans.UserBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by user on 2017/4/12.
 */

public class WriteToCSV {

    private static UserBean userBean = UserBean.getInstence();

    public static void writeAOG(ArrayList<AOG> aoglist, String name) {
        ArrayList<AOG> list = new ArrayList<>();
        list.addAll(aoglist);
        userBean.getAogArrayList().clear();

        if (!android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {//如果不存在SD卡,
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
        }
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡根目录
        fileDir += "/vervel/csv";
        File dirFile = new File(fileDir);
        if (!dirFile.exists()) {
           boolean iss =  dirFile.mkdirs();
//            Log.e("MSL", "writeAOG: filepath not exists,but i creat it :"  + iss);
        }

        File aogFile = new File(fileDir + "/" + name);

        if (!aogFile.exists()) {
            try {
                aogFile.createNewFile();
                addToFileByFileWriter(aogFile.getAbsolutePath(), "time,x,y,z\n");
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
        addToFileByFileWriter(aogFile.getAbsolutePath(), buffer.toString());
    }

    public static void writePalstance(ArrayList<Palstance> palstancelist, String name) {
        ArrayList<Palstance> list = new ArrayList<>();
        list.addAll(palstancelist);
        userBean.getPalstanceArrayList().clear();

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

    public static void writeMagnetism(ArrayList<Magnetism> MagnetismList, String name) {
        ArrayList<Magnetism> list = new ArrayList<>();
        list.addAll(MagnetismList);
        userBean.getMagnetismArrayList().clear();

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
