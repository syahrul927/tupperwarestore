package com.example.tupperwarestore.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.model.RegisterModel;
import com.example.tupperwarestore.model.resp.ResponseApi;
import com.example.tupperwarestore.retrofit.IAuthApi;
import com.example.tupperwarestore.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<Boolean> _isSuccess = new MutableLiveData<>();
    public MutableLiveData<RegisterModel> model = new MutableLiveData<>(new RegisterModel());
    private MutableLiveData<String> _errMessage = new MutableLiveData<>();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IAuthApi authApi;

    public RegisterViewModel() {
    }
    public List<RegisterModel> getList(){

        return new ArrayList<>();
    }

    public void doRegister(){
        if(model.getValue() != null){
                Retrofit retrofit = RetrofitClient.getInstance();
                authApi = retrofit.create(IAuthApi.class);
                authApi.register(model.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseApi>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ResponseApi responseApi) {
                        Log.d(TAG, "onSuccess: "+responseApi.toString());
                        if(responseApi.getStatus().equals(200)){
                            _isSuccess.postValue(true);
                        }else{
                            _isSuccess.postValue(false);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _errMessage.postValue(e.getMessage().toString());
                    }
                });

        }
    }

    private static final String TAG = "RegisterViewModel";

    public LiveData<String> getMessage(){
        return _errMessage;
    }
    public LiveData<Boolean> getIsSuccess(){
        return _isSuccess;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
