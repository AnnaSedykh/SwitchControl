package com.annasedykh.switchcontrol.data.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.annasedykh.switchcontrol.data.database.room.AppDatabase;
import com.annasedykh.switchcontrol.data.database.room.DatabaseRoomImpl;

public class DatabaseInitializer {

    public Database init(Context context){

        AppDatabase appDatabase = Room
                .databaseBuilder(context, AppDatabase.class, "switchcontrol.db")
                .build();
        return new DatabaseRoomImpl(appDatabase);
    }
}
