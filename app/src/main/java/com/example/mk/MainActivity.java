package com.example.mk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.example.mk.ui.account.AccountFragment;
import com.example.mk.ui.home.HomeFragment;
import com.example.mk.ui.lib.LibFragment;
import com.example.mk.ui.saves.SavesFragment;
import com.example.mk.ui.settings.SettingsFragment;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private User user;
    private boolean status = false;
    private static final int REQUEST_CODE_REGISTER = 1;
    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();
    LibFragment libFragment = new LibFragment();
    SavesFragment savesFragment = new SavesFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFragmentAndNavigation(savedInstanceState);

        checkLoginStatus();


    }

    private final ActivityResultLauncher<Intent> registrationActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    user = result.getData().getParcelableExtra("account_data");
                }
            });

    private void checkLoginStatus() {
        SharedPreferences preferences = getSharedPreferences("account_data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("user_data", null); // Чтение JSON из SharedPreferences
        if (json != null) {
            user = gson.fromJson(json, User.class); // Десериализация JSON в объект
            status=true;
        }
        if (status == false) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            registrationActivityResultLauncher.launch(intent);
        }
        if (status == true) {
            Intent intentA = new Intent(MainActivity.this, AccountFragment.class);
            intentA.putExtra(User.class.getSimpleName(), user);
        }
    }

    protected void initializeFragmentAndNavigation(Bundle savedInstanceState) {
        //Подключение фрагментов к нижней панели
        // Первоначальная загрузка фрагмента
        if (savedInstanceState == null) {
            loadFragment(homeFragment);
        }
        LinearLayout tabBarSaves = findViewById(R.id.tab_bar_saves);
        LinearLayout tabBarLibrary = findViewById(R.id.tab_bar_library);
        ImageView tabBarLogo = findViewById(R.id.tab_bar_logo);
        LinearLayout tabBarSettings = findViewById(R.id.tab_bar_settings);
        LinearLayout tabBarAccount = findViewById(R.id.tab_bar_account);
        tabBarSaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragmentSaves();
            }
        });
        tabBarLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragmentLib();
            }
        });
        tabBarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragmentHome();
            }
        });
        tabBarSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragmentSettings();
            }
        });
        tabBarAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragmentAccount();
            }
        });
    }

    // Метод для замены фрагментов
    private void loadFragment(Fragment fragment) {
        // Создание транзакции
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Замена фрагмента в контейнере
        fragmentTransaction.replace(R.id.fragment_container_view, fragment);

        // Добавление транзакции в бэкстек, чтобы пользователь мог вернуться назад
        fragmentTransaction.addToBackStack(null);

        // Подтверждение транзакции
        fragmentTransaction.commit();
    }

    public void switchToFragmentHome() {
        loadFragment(homeFragment);
    }

    public void switchToFragmentAccount() {
        loadFragment(accountFragment);
    }

    public void switchToFragmentLib() {
        loadFragment(libFragment);
    }

    public void switchToFragmentSaves() {
        loadFragment(savesFragment);
    }

    public void switchToFragmentSettings() {
        loadFragment(settingsFragment);
    }

}