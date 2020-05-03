package com.cybage.myapplication;

import com.cybage.myapplication.database.RecordsDBRepository;
import com.cybage.myapplication.model.Records;
import com.cybage.myapplication.utils.NetworkState;
import com.cybage.myapplication.viewmodel.DataUsageViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private DataUsageViewModel viewModel;

    @Mock
    Observer<PagedList<Records>> pagedListObserver;
    @Mock
    Observer<NetworkState> networkStateObserver;

    @Mock
    AppController appController;

    @Mock
    RecordsDBRepository recordsDBRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recordsDBRepository = new RecordsDBRepository(appController);
        viewModel = new DataUsageViewModel(appController);
        viewModel.getDataRecordsLiveData().observeForever(pagedListObserver);
        viewModel.getNetworkState().observeForever(networkStateObserver);
    }


    @Test
    public void pagedListTest(){
       // Mockito.when(recordsDBRepository.getAll()).thenReturn(pagedListObserver)


        //Mockito.verify(pagedListObserver).onChanged();
    }

}