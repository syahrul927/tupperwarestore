package com.example.tupperwarestore.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tupperwarestore.DashboardActivity;
import com.example.tupperwarestore.MainActivity;
import com.example.tupperwarestore.R;
import com.example.tupperwarestore.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.button.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Apakah anda yakin ingin Logout ?");
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            notificationsViewModel.logout(getContext());

                        }
                    })
                    .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
        });
        notificationsViewModel.getLogout().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });
        return root;
    }
}