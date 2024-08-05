package com.example.mk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListUtil.DataCallback;
import androidx.recyclerview.widget.AsyncListUtil;

import com.example.mk.errorDTO.ErrorDTO;
import com.example.mk.errorDTO.ErrorItemDTO;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private User user;
    private String url;
    TextView textViewName;
    TextView textViewEmail;
    TextView textViewPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText editName = findViewById(R.id.edit_text_name);
        EditText editEmail = findViewById(R.id.edit_text_email);
        EditText editPassword = findViewById(R.id.edit_text_password);
        Button buttonRegistration = findViewById(R.id.button_registration);
        textViewName = findViewById(R.id.text_view_name);
        textViewPassword = findViewById(R.id.text_view_password);
        textViewEmail = findViewById(R.id.text_view_email);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegistration(editName, editEmail, editPassword, buttonRegistration, view);
            }
        });
    }

    private void handleRegistration(EditText editName, EditText editEmail, EditText editPassword,Button buttonRegistration, View view) {
        if (editName.getText().toString().isEmpty()) {
            textViewName.setError("Введите имя!");
            textViewName.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
        }else{ textViewName.setTextColor(ContextCompat.getColor(view.getContext(), R.color.text_reg_default));}
        if (editName.getText().length() < 3) {
            textViewName.setError("Име должно быть длиннее 3 букв!");
            textViewName.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
        }else{ textViewName.setTextColor(ContextCompat.getColor(view.getContext(), R.color.text_reg_default));}
        if (editEmail.getText().toString().isEmpty()) {
            textViewEmail.setError("Введите почту!");
            textViewEmail.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
        }else{ textViewEmail.setTextColor(ContextCompat.getColor(view.getContext(), R.color.text_reg_default));}
        if (editPassword.getText().toString().isEmpty()) {
            textViewPassword.setError("Введите пароль!");
            textViewPassword.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
        }else{ textViewPassword.setTextColor(ContextCompat.getColor(view.getContext(), R.color.text_reg_default));}
        if (editName.getText().length() >= 3 &&
                !editName.getText().toString().isEmpty() &&
                !editEmail.getText().toString().isEmpty() &&
                !editPassword.getText().toString().isEmpty()) {
            user = new User(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
            buttonRegistration.setEnabled(false);
            //отправка данных на сервер
            postData(new RequestCallback<ErrorDTO>() {
                @Override
                public void onSuccess(ErrorDTO result) {
                    //сохранение данных в SharedPreferences
                    saveAccountDataToPreferences(user);
                    //передача данных на MainActivity
                    sendToDataToMain(user);
                    finish();
                }

                @Override
                public void onError(Throwable error) {
                    buttonRegistration.setEnabled(true);
                    // Обработка ошибки
                    Toast.makeText(RegistrationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private void sendToDataToMain(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("account_data", user);
        setResult(Activity.RESULT_OK, intent);

    }

    private void saveAccountDataToPreferences(User user) {
        SharedPreferences preferences = getSharedPreferences("account_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();//объект для сериализации
        String json = gson.toJson(user); // Сериализация объекта в JSON
        editor.putString("user_data", json); // Сохранение JSON в SharedPreferences
        editor.apply();
    }

    public interface RequestCallback<T> {
        void onSuccess(T result);
        void onError(Throwable error);
    }

    private void postData(RequestCallback<ErrorDTO> callback) {
        try {
            ServerApi serverApi = RetrofitClient.getClient().create(ServerApi.class);
            Call<ErrorDTO> call = serverApi.sendData(user);
            call.enqueue(new retrofit2.Callback<ErrorDTO>() {
                @Override
                public void onResponse(Call<ErrorDTO> call, Response<ErrorDTO> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    }
                    else {
                    try {
                        // Получение тела ошибки
                        String errorBody = response.errorBody().string();
                        // Парсинг ошибки (если она в формате JSON)
                        ErrorDTO errorDTO = new Gson().fromJson(errorBody, ErrorDTO.class);
                        // Вывод ошибок
                        for (ErrorItemDTO errorItem : errorDTO.getErrorItems()) {
                            if(errorItem.getField().equalsIgnoreCase("name")){
                                textViewName.setTextColor(ContextCompat.getColor(RegistrationActivity.this, R.color.red_error_1));
                                textViewName.setText(errorItem.getMessage());
                            }
                            if(errorItem.getField().equalsIgnoreCase("email")){
                                textViewEmail.setTextColor(ContextCompat.getColor(RegistrationActivity.this, R.color.red_error_1));
                                textViewEmail.setText(errorItem.getMessage());
                            }
                        }
                        callback.onError(new Exception("Try again!"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onError(e);
                        Toast.makeText(RegistrationActivity.this, "Error, проверьте подключение к интернету!", Toast.LENGTH_SHORT).show();
                    }

                    }
                }

                @Override
                public void onFailure(Call<ErrorDTO> call, Throwable t) {
                    t.printStackTrace();
                    callback.onError(t);
                }
            });
        } catch (IllegalArgumentException e) {
            Toast.makeText(RegistrationActivity.this, "Error, сервер недоступен", Toast.LENGTH_SHORT).show();
        }
    }
}
