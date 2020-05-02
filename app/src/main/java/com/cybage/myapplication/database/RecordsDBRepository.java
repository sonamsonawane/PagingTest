package com.cybage.myapplication.database;

import android.content.Context;
import android.os.AsyncTask;


import com.cybage.myapplication.model.Records;

import java.util.List;

import androidx.paging.DataSource;


public class RecordsDBRepository {

    private MyDatabase myDatabase;

    public RecordsDBRepository(Context context) {
        myDatabase = MyDatabase.getDatabase(context);
    }

    public void insertAll(final List<Records> dataModels) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                for(Records recordsList : dataModels){
                    String[] year = recordsList.getQuarter().split("-");
                    recordsList.setYear(year[0]);
                    recordsList.setQuarter(year[1]);
                    myDatabase.recordDao().insert(recordsList);
                }
                //myDatabase.recordDao().insertall(dataModels);
                return null;
            }
        }.execute();
    }


    public DataSource.Factory<Integer, Records> getAll() {
        DataSource.Factory<Integer, Records> result = myDatabase.recordDao().getAllRecords();
        return result;
    }
}