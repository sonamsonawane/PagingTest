package com.cybage.myapplication.rest;


import com.cybage.myapplication.model.NetworkRecords;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {

    @POST("datastore_search")
    Call<NetworkRecords> fetchRecords(
            @Query("resource_id") String resource_id,
            @Query("limit") int limit,
            @Query("offset") long offset);
}
