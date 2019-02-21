package com.annasedykh.switchcontrol.screens.dimmer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.annasedykh.switchcontrol.data.model.SwitchEntity;

public abstract class DimmerViewModel extends AndroidViewModel {

    public DimmerViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract LiveData<Boolean> updated();

    public abstract void updateSwitchSettings(SwitchEntity entity);
}
