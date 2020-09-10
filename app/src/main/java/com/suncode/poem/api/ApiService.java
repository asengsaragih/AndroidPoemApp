package com.suncode.poem.api;

import com.suncode.poem.model.Poem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/v1/randompoems")
    Call<List<Poem>> getPoem();
}
