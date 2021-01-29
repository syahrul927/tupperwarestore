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
        binding = ActivityWelcomeAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        App.token.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
              if(s!=null){
                  Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(i);
                  finish();
              }
            }
        });
    }
}