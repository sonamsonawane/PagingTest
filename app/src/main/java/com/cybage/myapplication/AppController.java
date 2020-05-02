package com.cybage.myapplication;

import android.app.Application;
import android.content.Context;


import com.cybage.myapplication.rest.RestApi;
import com.cybage.myapplication.rest.RestApiFactory;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AppController extends Application {

    private RestApi restApi;
    private Scheduler scheduler;
    private static AppController mContext;

    @Override
    public void onCreate() {
        if (mContext == null){
            mContext = this;
        }

        super.onCreate();
    }


    public static AppController create(Context context) {
        return mContext;
    }

    public RestApi getRestApi() {
        if(restApi == null) {
            restApi = RestApiFactory.create();
        }
        return restApi;
    }

    public void setRestApi(RestApi restApi) {
        this.restApi = restApi;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
