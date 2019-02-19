package com.annasedykh.switchcontrol;

import android.app.Application;

import com.annasedykh.switchcontrol.data.api.Api;
import com.annasedykh.switchcontrol.data.api.ApiInitializer;
import com.annasedykh.switchcontrol.data.database.Database;
import com.annasedykh.switchcontrol.data.database.DatabaseInitializer;

public class App extends Application {

    private Api api;
    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        api = new ApiInitializer().init();
        database = new DatabaseInitializer().init(this);
    }

    public Api getApi() {
        return api;
    }

    public Database getDatabase() {
        return database;
    }

}
