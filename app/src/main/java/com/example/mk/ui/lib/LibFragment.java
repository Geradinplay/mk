package com.example.mk.ui.lib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mk.AnimeItem;
import com.example.mk.R;
import com.example.mk.RetrofitClient;
import com.example.mk.ServerApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibFragment extends Fragment {
    private String name;
    private List<String> genres;
    private String posterUrl;
    private String description;
    private List<String> series;
    private int season;
    private List<AnimeItem> animeItems;
    private String serverUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(serverUrl!=null){
            RetrofitClient retrofit = new RetrofitClient();
            retrofit.getClient(serverUrl);

        }
        return inflater.inflate(R.layout.fragment_lib, container, false);
    }
    protected void fetchData(String serverUrl){
        try {
            RetrofitClient retrofitClient = new RetrofitClient();
            retrofitClient.getClient(serverUrl);
            ServerApi messagesApi = retrofitClient.getRetrofit().create(ServerApi.class);
            Call<AnimeItem> messages = messagesApi.getAnimeItems();
            messages.enqueue(new Callback<AnimeItem>(){
                @Override
                public void onResponse(Call<AnimeItem> call, Response<AnimeItem> response) {
                    name = response.body().getName();
                    genres = response.body().getGenres();
                    posterUrl = response.body().getPosterUrl();
                    description = response.body().getDescription();
                    series = response.body().getSeries();
                    season = response.body().getSeason();
                    }
                @Override
                public void onFailure(Call<AnimeItem> call, Throwable t) {

                }
            });
        } catch (IllegalArgumentException e) {
          Toast.makeText(getActivity(), "В настоящее время сервер недоступен!", Toast.LENGTH_SHORT).show();
        }
    }
}
