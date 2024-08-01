package com.example.mk;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerApi {
    @GET("items")
    Call<AnimeItem> getAnimeItems();;
}
