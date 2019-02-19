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

import io.reactivex.android.schedulers.AndroidSchedulers;
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
            loadViaApi();
        } else {
            getFromDB();
        }
    }

    private void getFromDB() {
        Disposable disposable = database.getSwitchList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            if (!list.isEmpty()) {
                                switchList.setValue(list);
                            } else {
                                loadViaApi();
                            }
                        });

        disposables.add(disposable);
    }

    private void loadViaApi() {

        Disposable disposable = api.switchList(QUERY_PARAM_METHOD)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        list -> {
                            database.saveSwitchList(list);
                        }, throwable -> Log.e(TAG, "onFailure: load switchList error ", throwable));

        disposables.add(disposable);
    }


    @Override
    public void onToggleSetChecked(boolean isChecked) {

    }
}
