package com.utang.vervel.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2017/4/7.
 */
public class AOG implements Parcelable {
    public AOG() {
    }

    private long time;
    private int velX;
    private int velY;
    private int velZ;

    protected AOG(Parcel in) {
        time = in.readLong();
        velX = in.readInt();
        velY = in.readInt();
        velZ = in.readInt();
    }

    public static final Creator<AOG> CREATOR = new Creator<AOG>() {
        @Override
        public AOG createFromParcel(Parcel in) {
            return new AOG(in);
        }

        @Override
        public AOG[] newArray(int size) {
            return new AOG[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeInt(velX);
        dest.writeInt(velY);
        dest.writeInt(velZ);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getVelZ() {
        return velZ;
    }

    public void setVelZ(int velZ) {
        this.velZ = velZ;
    }
}

