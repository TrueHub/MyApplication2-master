package com.utang.vervel.utils;

/**
 * Created by user on 2017/4/6.
 * 有关设备的常量池
 */

public class ConstantPool {
    //baseURL
    public static final String BASE_URL = "http://192.168.0.232:8080/";

    //BluetoothProfile
    public static final  java.util.UUID UUID_NOTIFY  = java.util.UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public static final  java.util.UUID UUID_WRITE  = java.util.UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");

    //instruct
    public static final byte HEAD = (byte) 0xAA;
    public static final byte END = (byte)0x55;
    public static final byte INSTRUCT_SEARCH_TIME = (byte)0x00;
    public static final byte INSTRUCT_SET_TIME = (byte)0x01;
    public static final byte INSTRUCT_SEARCH_PULSE = (byte)0x02;
    public static final byte INSTRUCT_HIS = (byte)0x03;
    public static final byte INSTRUCT_SEARCH_AOG_HIS = (byte)0x04;
    public static final byte INSTRUCT_SEARCH_PALSTANCE = (byte)0x05;
    public static final byte INSTRUCT_SEARCH_MAGNETISM = (byte)0x06;
    public static final byte INSTRUCT_SEARCH_PRESSURE = (byte)0x07;
    public static final byte INSTRUCT_DELETE_FLASH = (byte)0xFE;

    public static final byte[] SEARCH_DEVICE_TIME = new byte[]
            {HEAD,(byte)0x02, INSTRUCT_SEARCH_TIME, END};//查询设备时间

    public static final byte[] SEARCH_PULSE = new byte[]
        {HEAD,(byte)0x02, (byte) 0x01,INSTRUCT_SEARCH_PULSE, END};//查询心率,实时心率上传 [2]：0x01:on || 0x02:off

    public static final byte[] SEARCH_HIS = new byte[]
            {HEAD,(byte)0x02, INSTRUCT_HIS, END};//查询历史数据

    public static final byte[] DELETE_FLASH = new byte[]
            {HEAD,(byte)0x02,INSTRUCT_DELETE_FLASH,END};//清缓存
}
