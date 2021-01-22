package com.example.tupperwarestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tupperwarestore.databinding.ActivityWelcomeAuthBinding;
import com.example.tupperwarestore.databinding.FragmentLoginBinding;
import com.example.tupperwarestore.databinding.FragmentRegisterBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityWelcomeAuthBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
    }
}