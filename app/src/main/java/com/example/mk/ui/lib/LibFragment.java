package com.example.mk.ui.lib;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private String serverUrl="http://192.168.0.103:8080/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(serverUrl!=null){
            fetchData(serverUrl);
        }
        return inflater.inflate(R.layout.fragment_lib, container, false);
    }
    protected void updateUI(){

    }

    protected void fetchData(String serverUrl){
//        try {
//            RetrofitClient retrofitClient = new RetrofitClient();
//            retrofitClient.getClient(serverUrl);
//            ServerApi messagesApi = retrofitClient.getRetrofit().create(ServerApi.class);
//            Call<AnimeItem> messages = messagesApi.getAnimeItems();
//            messages.enqueue(new Callback<AnimeItem>(){
//                @Override
//                public void onResponse(Call<AnimeItem> call, Response<AnimeItem> response) {
//
//                    }
//                @Override
//                public void onFailure(Call<AnimeItem> call, Throwable t) {
//                    final Dialog dialogFragment = new Dialog(getContext()); // Объявляем dialogFragment как final
//                    dialogFragment.setContentView(R.layout.fragment_dialog);
//                    dialogFragment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    dialogFragment.getWindow().setGravity(Gravity.CENTER);
//
//                    TextView reloadButton = dialogFragment.findViewById(R.id.reload_button); // Находим кнопку внутри диалога
//                    reloadButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            fetchData(serverUrl);
//                            dialogFragment.dismiss(); // Закрываем диалог, используя dialogFragment
//                        }
//                    });
//
//                    dialogFragment.show(); // Отображаем диалог
//                }
//            });
//        } catch (IllegalArgumentException e) {
//          Toast.makeText(getActivity(), "В настоящее время сервер недоступен!", Toast.LENGTH_SHORT).show();
//        }
    }
}
