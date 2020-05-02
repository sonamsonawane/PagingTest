package com.cybage.myapplication.utils;

import com.cybage.myapplication.AppController;
import com.cybage.myapplication.database.RecordsDBRepository;
import com.cybage.myapplication.model.NetworkRecords;
import com.cybage.myapplication.model.Records;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagedListBoundaryCallback extends PagedList.BoundaryCallback<Records> {

    private final AppController appController;
    private RecordsDBRepository recordsDBRepository;
    AppExecutors executors;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public PagedListBoundaryCallback(RecordsDBRepository recordsDBRepository, AppExecutors executors, AppController appController) {
        super();
        this.recordsDBRepository = recordsDBRepository;
        this.executors = executors;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        this.appController = appController;

    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void onZeroItemsLoaded() {
        fetchFromNetwork(null);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Records itemAtFront) {
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Records itemAtEnd) {
         super.onItemAtEndLoaded(itemAtEnd);
         fetchFromNetwork(itemAtEnd);
    }

    public void fetchFromNetwork(Records user) {

        if (user == null) {
            user = new Records();
            user.setId(0);
        }
        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchRecords( "a807b7ab-6cad-4aa6-87d0-e283a7353a0f", 10, user.getId())
                .enqueue(new Callback<NetworkRecords>() {
                    @Override
                    public void onResponse(Call<NetworkRecords> call, Response<NetworkRecords> response) {
                        if(response.isSuccessful()) {
                            if (response.body() != null)
                            recordsDBRepository.insertAll(response.body().getResult().getRecords());
                            networkState.postValue(NetworkState.LOADED);
                        } else networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }

                    @Override
                    public void onFailure(Call<NetworkRecords> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });



    }

}