package com.annasedykh.switchcontrol.data.database.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.annasedykh.switchcontrol.data.model.SwitchEntity;

@Database(entities = {SwitchEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SwitchDao switchDao();
}
