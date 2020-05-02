package com.cybage.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
;import com.cybage.myapplication.AppController;
import com.cybage.myapplication.R;
import com.cybage.myapplication.adapters.DataUsageListAdapter;
import com.cybage.myapplication.databinding.DataUsageActivityBinding;
import com.cybage.myapplication.model.Records;
import com.cybage.myapplication.utils.ConnectionLiveData;
import com.cybage.myapplication.utils.ConnectionModel;
import com.cybage.myapplication.utils.NetworkState;
import com.cybage.myapplication.viewmodel.DataUsageViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DataUsageActivity extends AppCompatActivity {

    private DataUsageListAdapter dataUsageListAdapter;
    private DataUsageViewModel dataUsageViewModel;
    private DataUsageActivityBinding binding;
    private PagedList<Records> pagedListtemp;
    private ConnectionLiveData connectionLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Step 1: Using DataBinding, we setup the layout for the activity
         *
         * */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*
         * Step 2: Initialize the ViewModel
         *
         * */
        dataUsageViewModel = new DataUsageViewModel(AppController.create(this));

        /*
         * Step 2: Setup the dataUsageListAdapter class for the RecyclerView
         *
         * */
        binding.listMobileDataUsage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dataUsageListAdapter = new DataUsageListAdapter(getApplicationContext(), this);


        /*
         * Step 4: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        binding.llprogress.setVisibility(View.VISIBLE);
        binding.textError.setVisibility(View.GONE);
        dataUsageViewModel.getDataRecordsLiveData().observe(this, pagedList -> {
            pagedListtemp = pagedList;
            if (pagedList.size() > 0) {
                binding.llprogress.setVisibility(View.GONE);
            }
            dataUsageListAdapter.submitList(pagedList);
        });

        /*
         * Step 5: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        dataUsageViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState.getStatus() == NetworkState.Status.FAILED){
                if (pagedListtemp.size() == 0){
                   showError(networkState.getMsg());
                }
            }
            dataUsageListAdapter.setNetworkState(networkState);
        });

        binding.listMobileDataUsage.setAdapter(dataUsageListAdapter);


        connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                if (connection.getIsConnected()) {
                    dataUsageViewModel.callNetworkApiAndupdateDB();
                } else {
                    if (pagedListtemp.size() > 0)
                        Toast.makeText(DataUsageActivity.this, String.format("Please check you internet connection."), Toast.LENGTH_SHORT).show();
                    else{
                        binding.llprogress.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.textError.setVisibility(View.VISIBLE);
                        binding.textError.setText("Internet Connection OFF");
                    }

                }
            }
        });
    }

    private void showError(String msg) {
        binding.progressBar.setVisibility(View.GONE);
        binding.textError.setVisibility(View.VISIBLE);
        binding.textError.setText(msg);
    }
}
