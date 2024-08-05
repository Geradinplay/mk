package com.example.mk;

import com.example.mk.errorDTO.ErrorDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerApi {
    @POST("/user")
    Call<ErrorDTO> sendData(@Body User user);
}
