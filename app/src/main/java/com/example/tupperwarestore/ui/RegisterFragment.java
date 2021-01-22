package com.example.tupperwarestore.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupperwarestore.R;
import com.example.tupperwarestore.viewModel.RegisterViewModel;
import com.example.tupperwarestore.databinding.FragmentRegisterBinding;
import com.example.tupperwarestore.model.RegisterModel;

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
//        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        model = new RegisterModel();
        binding.setRegister(model);
        binding.backButton.setOnClickListener(v ->{
            NavDirections action = RegisterFragmentDirections.actionRegisterFragmentPop();
            NavHostFragment.findNavController(this).navigate(action);
        });

        binding.btnRegister.setOnClickListener(v -> {
            binding.setRegister(model);
            //do hit api register
        });

        return binding.getRoot();

    }

//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
//    }

}