package com.annasedykh.switchcontrol.screens.dimmer;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.annasedykh.switchcontrol.App;
import com.annasedykh.switchcontrol.data.api.Api;
import com.annasedykh.switchcontrol.data.database.Database;
import com.annasedykh.switchcontrol.data.model.SwitchEntity;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DimmerViewModelImpl extends DimmerViewModel {
    private static final String TAG = "DimmerViewModel";

    private MutableLiveData<Boolean> updated = new MutableLiveData<>();

    private Api api;
    private Database database;
    private CompositeDisposable disposables = new CompositeDisposable();


    public DimmerViewModelImpl(@NonNull Application application) {
        super(application);

        api = ((App) application).getApi();
        database = ((App) application).getDatabase();
    }

    @Override
    public LiveData<Boolean> updated() {
        return updated;
    }

    @Override
    public void updateSwitchSettings(SwitchEntity switchEntity) {

        Disposable disposable = api.updateSwitch(switchEntity)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        Void -> {
                            database.updateSwitch(switchEntity);
                            updated.postValue(true);
                        },
                        throwable -> {
                            Log.e(TAG, "onFailure: update switch error ", throwable);
                            updated.postValue(false);
                        });

        disposables.add(disposable);
    }
}
