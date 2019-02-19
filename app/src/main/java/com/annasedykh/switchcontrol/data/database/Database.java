package com.annasedykh.switchcontrol.data.database;

import com.annasedykh.switchcontrol.data.model.SwitchEntity;

import java.util.List;

import io.reactivex.Flowable;

public interface Database {

    void saveSwitchList(List<SwitchEntity> switchList);

    Flowable<List<SwitchEntity>> getSwitchList();

    SwitchEntity getSwitch(String id);

    void updateSwitch(SwitchEntity switchEntity);
}
