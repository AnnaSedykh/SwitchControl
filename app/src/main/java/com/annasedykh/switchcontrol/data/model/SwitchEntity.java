package com.annasedykh.switchcontrol.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Switch")
public class SwitchEntity {

    public static final String STATUS_DISABLED = "255";
    public static final String STATUS_OFF = "0";
    public static final String STATUS_MAX = "1";

    @NonNull
    @PrimaryKey
    public String id;

    public String name;

    public String status0;

    public String status1;

}
