package com.example.tupperwarestore.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tupperwarestore.DashboardActivity;
import com.example.tupperwarestore.viewModel.LoginViewModel;
import com.example.tupperwarestore.R;
import com.example.tupperwarestore.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    public LoginFragment() {
        // Required empty public constructor
    }

    private FragmentLoginBinding binding;

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.backButton.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentPop();
            NavHostFragment.findNavController(this).navigate(action);
        });
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLogin(mViewModel);
        binding.btnLogin.setOnClickListener(v->{
            //do hit api
           mViewModel.doLogin();
//
        });
        mViewModel.isProgress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.lavThumbUp.setVisibility(View.VISIBLE);
                }else {
                    binding.lavThumbUp.setVisibility(View.INVISIBLE);
                }
            }
        });

        mViewModel.getSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    mViewModel.insertDetailUser(getContext());
                }
            }
        });

        mViewModel.getErrMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(),"Email atau Password Salah", Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }
}