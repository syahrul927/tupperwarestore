package com.example.tupperwarestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
        Activity activity = this;
        binding = ActivityWelcomeAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        App.token.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent i = new Intent(activity, DashboardActivity.class);
                startActivity(i);
                activity.finish();
            }
        });
    }
}