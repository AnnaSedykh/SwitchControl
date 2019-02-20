package com.annasedykh.switchcontrol.screens.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.annasedykh.switchcontrol.data.model.SwitchEntity;

import java.util.List;

public abstract class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract void loadSwitchList(boolean fromRefresh);

    public abstract LiveData<List<SwitchEntity>> switchList();

    public abstract void onToggleSetChecked(String id, boolean isChecked);

}
