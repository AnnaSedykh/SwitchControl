package com.annasedykh.switchcontrol.data.api;

import com.annasedykh.switchcontrol.data.model.SwitchEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("config.php")
    Observable<List<SwitchEntity>> switchList(@Query("method") String method);

    @POST("config.php")
    Observable<Void> updateSwitch(@Body SwitchEntity switchEntity);
}
