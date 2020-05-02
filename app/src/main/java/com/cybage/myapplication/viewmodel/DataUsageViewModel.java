package com.cybage.myapplication.viewmodel;

import com.cybage.myapplication.AppController;
import com.cybage.myapplication.database.RecordsDBRepository;
import com.cybage.myapplication.model.Records;
import com.cybage.myapplication.utils.AppExecutors;
import com.cybage.myapplication.utils.NetworkState;
import com.cybage.myapplication.utils.PagedListBoundaryCallback;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class DataUsageViewModel extends ViewModel {

    private final RecordsDBRepository recordsDBRepository;
    private final AppExecutors executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Records>> dataRecordsLiveData;


    private AppController appController;
    private PagedListBoundaryCallback pagedListBoundaryCallback;

    public DataUsageViewModel(@NonNull AppController appController) {
        this.appController = appController;
        executor = new AppExecutors();
        recordsDBRepository = new RecordsDBRepository(appController);

        init();
    }

    private void init() {


        pagedListBoundaryCallback = new PagedListBoundaryCallback(recordsDBRepository, executor, appController);
        networkState = pagedListBoundaryCallback.getNetworkState();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20).build();


        dataRecordsLiveData = (new LivePagedListBuilder(recordsDBRepository.getAll(), pagedListConfig))
                .setBoundaryCallback(pagedListBoundaryCallback)
                .build();

    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Records>> getDataRecordsLiveData() {
        return dataRecordsLiveData;
    }

    public void callNetworkApiAndupdateDB() {
        pagedListBoundaryCallback.fetchFromNetwork(null);
    }
}
