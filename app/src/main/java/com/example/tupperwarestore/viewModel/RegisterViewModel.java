package com.example.tupperwarestore.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.model.RegisterModel;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterModel> model = new MutableLiveData<>();

    public void setModel(RegisterModel model) {
        this.model.setValue(model);
    }

    public RegisterViewModel() {
    }

    public void doRegister(){
        if(model.getValue() != null){
            Log.d("Register Model ",model.getValue().getNama());
        }
    }


    public LiveData<RegisterModel> getRegister(){
        return model;
    }
}
