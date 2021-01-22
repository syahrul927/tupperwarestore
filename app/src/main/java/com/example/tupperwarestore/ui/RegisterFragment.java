package com.example.tupperwarestore.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tupperwarestore.R;
import com.example.tupperwarestore.model.resp.ResponseApi;
import com.example.tupperwarestore.retrofit.IAuthApi;
import com.example.tupperwarestore.retrofit.RetrofitClient;
import com.example.tupperwarestore.viewModel.RegisterViewModel;
import com.example.tupperwarestore.databinding.FragmentRegisterBinding;
import com.example.tupperwarestore.model.RegisterModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }

    private RegisterModel model;
    private RegisterViewModel mViewModel;

    private FragmentRegisterBinding binding;
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegister(mViewModel);
        binding.backButton.setOnClickListener(v ->{
            NavDirections action = RegisterFragmentDirections.actionRegisterFragmentPop();
            NavHostFragment.findNavController(this).navigate(action);
        });

        binding.btnRegister.setOnClickListener(v -> {
            mViewModel.doRegister();
        });


        mViewModel.getMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(),s, Toast.LENGTH_LONG).show();
            }
        });

        mViewModel.getIsSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    closeFragment();
                }
            }
        });
        return binding.getRoot();

    }

    private void closeFragment() {
        Toast.makeText(getContext(), "Register Success", Toast.LENGTH_LONG).show();
        NavDirections action = RegisterFragmentDirections.actionRegisterFragmentPop();
        NavHostFragment.findNavController(this).navigate(action);
    }

}