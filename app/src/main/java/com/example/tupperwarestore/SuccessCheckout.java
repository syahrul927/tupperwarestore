package com.example.tupperwarestore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupperwarestore.databinding.FragmentSuccessCheckoutBinding;
import com.example.tupperwarestore.ui.order.OrderViewModel;

public class SuccessCheckout extends Fragment {

    FragmentSuccessCheckoutBinding binding;

    private OrderViewModel orderViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        orderViewModel =
                new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        binding = FragmentSuccessCheckoutBinding.inflate(inflater, container, false);
        binding.konfirmasi.setOnClickListener(v -> {
            orderViewModel.setIsSuccessCheckout(false);
            NavDirections action = SuccessCheckoutDirections.actionSuccessCheckoutPop();
            NavHostFragment.findNavController(this).navigate(action);
        });
        return binding.getRoot();
    }
}