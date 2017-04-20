package com.utang.vervel.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017/4/7.
 */
public class Magnetism implements Parcelable {
    public Magnetism() {
    }

    private long time;
    private int strengthX;
    private int strengthY;
    private int strengthZ;

    protected Magnetism(Parcel in) {
        time = in.readLong();
        strengthX = in.readInt();
        strengthY = in.readInt();
        strengthZ = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeInt(strengthX);
        dest.writeInt(strengthY);
        dest.writeInt(strengthZ);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Magnetism> CREATOR = new Creator<Magnetism>() {
        @Override
        public Magnetism createFromParcel(Parcel in) {
            return new Magnetism(in);
        }

        @Override
        public Magnetism[] newArray(int size) {
            return new Magnetism[size];
        }
    };

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStrengthX() {
        return strengthX;
    }

    public void setStrengthX(int strengthX) {
        this.strengthX = strengthX;
    }

    public int getStrengthY() {
        return strengthY;
    }

    public void setStrengthY(int strengthY) {
        this.strengthY = strengthY;
    }

    public int getStrengthZ() {
        return strengthZ;
    }

    public void setStrengthZ(int strengthZ) {
        this.strengthZ = strengthZ;
    }
}
