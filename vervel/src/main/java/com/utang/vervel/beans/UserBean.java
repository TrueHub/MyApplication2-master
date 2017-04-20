package com.utang.vervel.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by user on 2017/4/11.
 * 单例user类
 */

public class UserBean implements Parcelable {
    public ArrayList<AOG> aogArrayList = new ArrayList<>();
    public ArrayList<Magnetism> magnetismArrayList = new ArrayList<>();
    public ArrayList<Palstance> palstanceArrayList = new ArrayList<>();
    public ArrayList<Pressure> pressureArrayList = new ArrayList<>();
    public ArrayList<Pulse> pulseArrayList = new ArrayList<>();

    private UserBean() {
    }

    private static class UserHolder {
        private static final UserBean userBean = new UserBean();
    }

    public static UserBean getInstence() {
        return UserHolder.userBean;
    }

    protected UserBean(Parcel in) {
        aogArrayList = in.createTypedArrayList(AOG.CREATOR);
        magnetismArrayList = in.createTypedArrayList(Magnetism.CREATOR);
        palstanceArrayList = in.createTypedArrayList(Palstance.CREATOR);
        pressureArrayList = in.createTypedArrayList(Pressure.CREATOR);
        pulseArrayList = in.createTypedArrayList(Pulse.CREATOR);
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(aogArrayList);
        dest.writeTypedList(magnetismArrayList);
        dest.writeTypedList(palstanceArrayList);
        dest.writeTypedList(pressureArrayList);
        dest.writeTypedList(pulseArrayList);
    }

    public ArrayList<AOG> getAogArrayList() {
        return aogArrayList;
    }

    public void setAogArrayList(ArrayList<AOG> aogArrayList) {
        this.aogArrayList = aogArrayList;
    }

    public ArrayList<Magnetism> getMagnetismArrayList() {
        return magnetismArrayList;
    }

    public void setMagnetismArrayList(ArrayList<Magnetism> magnetismArrayList) {
        this.magnetismArrayList = magnetismArrayList;
    }

    public ArrayList<Palstance> getPalstanceArrayList() {
        return palstanceArrayList;
    }

    public void setPalstanceArrayList(ArrayList<Palstance> palstanceArrayList) {
        this.palstanceArrayList = palstanceArrayList;
    }

    public ArrayList<Pressure> getPressureArrayList() {
        return pressureArrayList;
    }

    public void setPressureArrayList(ArrayList<Pressure> pressureArrayList) {
        this.pressureArrayList = pressureArrayList;
    }

    public ArrayList<Pulse> getPulseArrayList() {
        return pulseArrayList;
    }

    public void setPulseArrayList(ArrayList<Pulse> pulseArrayList) {
        this.pulseArrayList = pulseArrayList;
    }

    public static Creator<UserBean> getCREATOR() {
        return CREATOR;
    }
}
