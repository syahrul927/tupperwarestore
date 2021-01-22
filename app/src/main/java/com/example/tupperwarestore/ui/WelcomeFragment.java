package com.example.tupperwarestore.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupperwarestore.databinding.FragmentWelcomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        binding.btnRegister.setOnClickListener(v -> {
            NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment();
            NavHostFragment.findNavController(this).navigate(action);
        });

        binding.btnLogin.setOnClickListener(v -> {
            NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment();
            NavHostFragment.findNavController(this).navigate(action);
        });
        return binding.getRoot();

    }
}