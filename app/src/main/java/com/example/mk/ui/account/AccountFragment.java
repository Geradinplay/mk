package com.example.mk.ui.account;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.mk.R;
import com.example.mk.RetrofitClient;
import com.example.mk.ServerApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment implements InteractionListener{
    private String name;
    private String email;
    private String password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Изначальная установка дочернего фрагмента
        if(savedInstanceState==null){
            loadFragment(new RegistrationFragment());
        }

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onSwitchToLoginFragment() {
        loadFragment(new LoginFragment());
    }

    @Override
    public void onSwitchToRegistrationFragment() {
        loadFragment(new RegistrationFragment());
    }

    private void loadFragment(Fragment fragment){
        // Создание транзакции
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Замена фрагмента в контейнере
        fragmentTransaction.replace(R.id.container_inner_fragment_account, fragment);

        // Добавление транзакции в бэкстек, чтобы пользователь мог вернуться назад
        fragmentTransaction.addToBackStack(null);

        // Подтверждение транзакции
        fragmentTransaction.commit();
    }
}
