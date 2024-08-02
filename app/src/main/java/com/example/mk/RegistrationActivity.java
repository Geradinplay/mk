package com.example.mk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListUtil.DataCallback;
import androidx.recyclerview.widget.AsyncListUtil;

import com.google.gson.Gson;

public class RegistrationActivity extends AppCompatActivity {
private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText editName = findViewById(R.id.edit_text_name);
        EditText editEmail = findViewById(R.id.edit_text_email);
        EditText editPassword = findViewById(R.id.edit_text_password);
        Button buttonRegistration = findViewById(R.id.button_registration);
        TextView textViewName = findViewById(R.id.text_view_name);
        TextView textViewPassword = findViewById(R.id.text_view_password);
        TextView textViewEmail = findViewById(R.id.text_view_email);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editName.getText().toString().isEmpty()||editName.getText().length()<3){
                    textViewName.setError("Введите имя!");
                    textViewName.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
                }
                if (editName.getText().length()<3){
                    textViewName.setError("Име должно быть длиннее 3 букв!");
                    textViewName.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
                }
                if (editEmail.getText().toString().isEmpty()){
                    textViewEmail.setError("Введите почту!");
                    textViewEmail.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
                }
                if (editPassword.getText().toString().isEmpty()){
                    textViewPassword.setError("Введите пароль!");
                    textViewPassword.setTextColor(ContextCompat.getColor(view.getContext(), R.color.red_error_1));
                }
                if(!editName.getText().toString().isEmpty()&&!editEmail.getText().toString().isEmpty()&&!editPassword.getText().toString().isEmpty()){
                    user = new User(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                    //передача данных на MainActivity
                    sendToDataToMain(user);
                    //сохранение данных в SharedPreferences
                    saveAccountDataToPreferences(user);
                    //отправка данных на сервер
                    finish();//ДОЛЖЕН ВЫПОЛНЯТСЯ ПОСЛЕ ОТВЕТА СЕРВЕРА
                }
            }
        });
    }
    private void postData(final AsyncListUtil.DataCallback callback) {

    }

    private void sendToDataToMain(User user){
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


    private void postData() {

    }
}
