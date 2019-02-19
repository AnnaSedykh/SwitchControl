package com.annasedykh.switchcontrol.data.api;

import com.annasedykh.switchcontrol.data.api.model.ServerResponse;
import com.annasedykh.switchcontrol.data.api.model.SwitchEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET
    Observable<ServerResponse> switchList(@Query("method") String method);

    @POST
    Observable<Object> updateSwitch(@Body SwitchEntity switchEntity);
}
