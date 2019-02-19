package com.annasedykh.switchcontrol.data.database.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.annasedykh.switchcontrol.data.api.model.SwitchEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface SwitchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveSwitchList(List<SwitchEntity> switchList);

    @Query("SELECT * FROM Switch")
    Flowable<List<SwitchEntity>> getSwitchList();

    @Query("SELECT * FROM Switch WHERE id = :id")
    SwitchEntity getSwitch(String id);

    @Update
    void updateSwitch(SwitchEntity switchEntity);

}
