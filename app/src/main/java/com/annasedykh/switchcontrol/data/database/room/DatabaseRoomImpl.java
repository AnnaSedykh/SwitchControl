package com.annasedykh.switchcontrol.data.database.room;

import com.annasedykh.switchcontrol.data.api.model.SwitchEntity;
import com.annasedykh.switchcontrol.data.database.Database;

import java.util.List;

import io.reactivex.Flowable;

public class DatabaseRoomImpl implements Database {

    private AppDatabase database;

    public DatabaseRoomImpl(AppDatabase database) {
        this.database = database;
    }

    @Override
    public void saveSwitchList(List<SwitchEntity> switchList) {
        database.switchDao().saveSwitchList(switchList);
    }

    @Override
    public Flowable<List<SwitchEntity>> getSwitchList() {
        return database.switchDao().getSwitchList();
    }

    @Override
    public SwitchEntity getSwitch(String id) {
        return database.switchDao().getSwitch(id);
    }

    @Override
    public void updateSwitch(SwitchEntity switchEntity) {
        database.switchDao().updateSwitch(switchEntity);
    }
}
