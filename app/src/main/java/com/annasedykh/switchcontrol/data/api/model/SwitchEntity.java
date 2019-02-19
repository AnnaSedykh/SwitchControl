package com.annasedykh.switchcontrol.data.api.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Switch")
public class SwitchEntity {

    @NonNull
    @PrimaryKey
    public String id;

    public String name;

    public String status0;

    public String status1;

}
