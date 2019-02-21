package com.annasedykh.switchcontrol.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "Switch")
public class SwitchEntity implements Parcelable {

    public static final String SWITCH = "switch";
    public static final String STATUS_DISABLED = "255";
    public static final String STATUS_OFF = "0";
    public static final String STATUS_MAX = "1";

    public SwitchEntity(@NonNull String id, String name, String status0, String status1) {
        this.id = id;
        this.name = name;
        this.status0 = status0;
        this.status1 = status1;
    }

    @NonNull
    @PrimaryKey
    public String id;

    public String name;

    public String status0;

    public String status1;

    protected SwitchEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        status0 = in.readString();
        status1 = in.readString();
    }

    public static final Creator<SwitchEntity> CREATOR = new Creator<SwitchEntity>() {
        @Override
        public SwitchEntity createFromParcel(Parcel in) {
            return new SwitchEntity(in);
        }

        @Override
        public SwitchEntity[] newArray(int size) {
            return new SwitchEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(status0);
        dest.writeString(status1);
    }
}
