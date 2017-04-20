package com.utang.vervel.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.utang.vervel.beans.AOG;
import com.utang.vervel.beans.Magnetism;
import com.utang.vervel.beans.Palstance;
import com.utang.vervel.beans.Pressure;
import com.utang.vervel.beans.Pulse;
import com.utang.vervel.beans.UserBean;
import com.utang.vervel.utils.DateUtils;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private String createTabSql1, createTabSql2, createTabSql3, createTabSql4, createTabSql5;
    private UserBean userBean = UserBean.getInstence();

    public SqliteHelper(Context context) {
        super(context, "health.db", null, 1);

        createTabSql1 = "CREATE TABLE if not exists AOG(_id integer primary key autoincrement, timeLong integer,timeStr text, x integer,y integer,z integer)";
        createTabSql2 = "CREATE TABLE if not exists Magnetism(_id integer primary key autoincrement, timeLong integer, timeStr text, x integer,y integer,z integer)";
        createTabSql3 = "CREATE TABLE if not exists Palstance(_id integer primary key autoincrement, timeLong integer, timeStr text, x integer,y integer,z integer)";
        createTabSql4 = "CREATE TABLE if not exists Pressure(_id integer primary key autoincrement, timeLong integer, timeStr text, intensity integer)";
        createTabSql5 = "CREATE TABLE if not exists Pulse(_id integer primary key autoincrement, timeLong integer, timeStr text, pulse integer)";
        db = getReadableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("MSL", "onCreateSQL: ");
        db.execSQL(createTabSql1);
        db.execSQL(createTabSql2);
        db.execSQL(createTabSql3);
        db.execSQL(createTabSql4);
        db.execSQL(createTabSql5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertDataBySw() {
        if (userBean.getAogArrayList().size() != 0) {
            addAOGBySw(userBean.getAogArrayList());
            userBean.getAogArrayList().clear();
        }
        if (userBean.getPulseArrayList().size() != 0) {
            addPulseBySw(userBean.getPulseArrayList());
            userBean.getPulseArrayList().clear();
        }
        if (userBean.getPalstanceArrayList().size() != 0) {
            addPalstanceBySw(userBean.getPalstanceArrayList());
            userBean.getPalstanceArrayList().clear();
        }
        if (userBean.getMagnetismArrayList().size() != 0) {
            addMagnetismBySw(userBean.getMagnetismArrayList());
            userBean.getMagnetismArrayList().clear();
        }
        if (userBean.getPressureArrayList().size() != 0) {
            addPressureBySw(userBean.getPressureArrayList());
            userBean.getPressureArrayList().clear();
        }
    }

    public void addAOGBySw(ArrayList<AOG> list) {
        db.beginTransaction();
        try {
            String time;
            int x, y, z;
            for (int i = 0; i < list.size(); i++) {
                time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
                x = list.get(i).getVelX();
                y = list.get(i).getVelY();
                z = list.get(i).getVelZ();
                ContentValues values = new ContentValues();
                values.put("timeLong", list.get(i).getTime());
                values.put("timeStr", time);
                values.put("x", x);
                values.put("y", y);
                values.put("z", z);
                db.insert("AOG", "_id", values);

            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.endTransaction();
    }

    public void addMagnetismBySw(ArrayList<Magnetism> list) {
        db.beginTransaction();
        try {
            String time;
            int x, y, z;
            for (int i = 0; i < list.size(); i++) {
                time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
                x = list.get(i).getStrengthX();
                y = list.get(i).getStrengthY();
                z = list.get(i).getStrengthZ();
                ContentValues values = new ContentValues();
                values.put("timeLong", list.get(i).getTime());
                values.put("timeStr", time);
                values.put("x", x);
                values.put("y", y);
                values.put("z", z);
                db.insert("Magnetism", "_id", values);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.endTransaction();
    }

    public void addPalstanceBySw(ArrayList<Palstance> list) {
        db.beginTransaction();
        try {
            String time;
            int x, y, z;
            for (int i = 0; i < list.size(); i++) {
                time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
                x = list.get(i).getVelX();
                y = list.get(i).getVelY();
                z = list.get(i).getVelZ();
                ContentValues values = new ContentValues();
                values.put("timeLong", list.get(i).getTime());
                values.put("timeStr", time);
                values.put("x", x);
                values.put("y", y);
                values.put("z", z);
                db.insert("Palstance", "_id", values);

            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.endTransaction();
    }

    public void addPressureBySw(ArrayList<Pressure> list) {
        db.beginTransaction();  //开启事务
        try {
            String time;
            long intensity;
            for (int i = 0; i < list.size(); i++) {
                time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
                intensity = list.get(i).getIntensityOfPressure();
                ContentValues values = new ContentValues();
                values.put("timeLong", list.get(i).getTime());
                values.put("timeStr", time);
                values.put("intensity", intensity);
                db.insert("Pressure", "_id", values);

            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.endTransaction();
    }

    public void addPulseBySw(ArrayList<Pulse> list) {
        Log.i("MSL", "addPulseBySw: " + list.size());
        db.beginTransaction();  //开启事务
        try {
            String time;
            long pulse;
            for (int i = 0; i < list.size(); i++) {
                time = DateUtils.getDateToString(list.get(i).getTime() * 1000);
                pulse = list.get(i).getPulse();
                ContentValues values = new ContentValues();
                values.put("timeLong", list.get(i).getTime());
                values.put("timeStr", time);
                values.put("pulse", pulse);
                db.insert("Pulse", "_id", values);
            }
            Log.i("MSL", "addPulseBySw: OK");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.endTransaction();
    }

}
