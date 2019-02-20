package com.annasedykh.switchcontrol.screens.main;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.annasedykh.switchcontrol.App;
import com.annasedykh.switchcontrol.data.api.Api;
import com.annasedykh.switchcontrol.data.database.Database;
import com.annasedykh.switchcontrol.data.model.SwitchEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModelImpl extends MainViewModel {

    private static final String TAG = "MainViewModel";

    private static final String QUERY_PARAM_METHOD = "status";

    private MutableLiveData<List<SwitchEntity>> switchList = new MutableLiveData<>();

    private Api api;
    private Database database;
    private CompositeDisposable disposables = new CompositeDisposable();

    public MainViewModelImpl(@NonNull Application application) {
        super(application);

        api = ((App) application).getApi();
        database = ((App) application).getDatabase();
    }

    @Override
    public LiveData<List<SwitchEntity>> switchList() {
        return switchList;
    }

    //Get switchList from DB or load from server
    @Override
    public void loadSwitchList(boolean fromRefresh) {
        if (fromRefresh) {
            loadSwitchListViaApi();
        } else {
            getSwitchListFromDB();
        }
    }

    private void getSwitchListFromDB() {
        Disposable disposable = database.getSwitchList()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        list -> {
                            if (!list.isEmpty()) {
                                switchList.postValue(list);
                            } else {
                                loadSwitchListViaApi();
                            }
                        });

        disposables.add(disposable);
    }

    private void loadSwitchListViaApi() {

        Disposable disposable = api.switchList(QUERY_PARAM_METHOD)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        list -> database.saveSwitchList(list),
                        throwable -> Log.e(TAG, "onFailure: load switchList error ", throwable));

        disposables.add(disposable);
    }


    @Override
    public void onToggleSetChecked(String id, boolean isChecked) {
        Disposable disposable = Observable.fromCallable(() ->
                database.getSwitch(id))
                .subscribeOn(Schedulers.io())
                .subscribe(switchEntity -> {
                    if (switchEntity != null) {
                        if (!SwitchEntity.STATUS_DISABLED.equals(switchEntity.status0)) {
                            switchEntity.status0 = isChecked ? SwitchEntity.STATUS_MAX : SwitchEntity.STATUS_OFF;

                        }
                        if (!SwitchEntity.STATUS_DISABLED.equals(switchEntity.status1)) {
                            switchEntity.status1 = isChecked ? SwitchEntity.STATUS_MAX : SwitchEntity.STATUS_OFF;
                        }
                        updateSwitch(switchEntity);
                    }
                });

        disposables.add(disposable);
    }

    private void updateSwitch(SwitchEntity switchEntity) {
        Disposable disposable = api.updateSwitch(switchEntity)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        Void -> database.updateSwitch(switchEntity),
                        throwable -> Log.e(TAG, "onFailure: update switch error ", throwable));

        disposables.add(disposable);
    }

}
