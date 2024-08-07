package com.example.mk.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mk.R;
import com.example.mk.databinding.FragmentAccountBinding;
import com.example.mk.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {
    private FragmentRegistrationBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fragmentRegistrationTextViewSwitchToLogin.setOnClickListener(v -> {
            if (getParentFragment() instanceof InteractionListener) {
                ((InteractionListener) getParentFragment()).onSwitchToLoginFragment();
            }
        });
    }
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Освобождаем ссылку на binding
    }
}
