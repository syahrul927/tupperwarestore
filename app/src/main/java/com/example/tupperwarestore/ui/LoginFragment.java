package com.example.tupperwarestore.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        binding.btnLogin.setOnClickListener(v->{
            //do hit api

            Intent i = new Intent(requireActivity(), DashboardActivity.class);
            startActivity(i);
            //using require activity for null safe yak :)OCee
            requireActivity().finish();
        });
        return binding.getRoot();
    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//    }
}