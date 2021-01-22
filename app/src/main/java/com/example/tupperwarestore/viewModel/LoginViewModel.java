package com.example.tupperwarestore.viewModel;

import android.app.Application;
import android.content.Context;
import android.service.autofill.UserData;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.App;
import com.example.tupperwarestore.model.LoginModel;
import com.example.tupperwarestore.model.resp.ResponseApi;
import com.example.tupperwarestore.model.resp.UserDetailResp;
import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.repository.UserDao;
import com.example.tupperwarestore.repository.UserDatabase;
import com.example.tupperwarestore.retrofit.IAuthApi;
import com.example.tupperwarestore.retrofit.RetrofitClient;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<LoginModel> model = new MutableLiveData<>(new LoginModel());
    private MutableLiveData<Boolean> _isSuccess = new MutableLiveData<>();
    private User user = null;
    private MutableLiveData<String> _errMessage = new MutableLiveData<>();

    private UserDao dao = null;
    private static final String TAG = "LoginViewModel";

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IAuthApi authApi;

    public LoginViewModel(MutableLiveData<LoginModel> model) {
        this.model = model;
    }

    public LoginViewModel() {
    }


    public void insertDetailUser(Context context){
        try {
            //loading
            dao = UserDatabase.getInstance(context).dao();
            dao.insert(user).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: SUCCESS LOGIN, Update token APPLICATION");
                            App.updateToken(user.getToken());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e(TAG, "onError: FAILED INSERT", e);
                        }
                    });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void doLogin(){
        if(model.getValue() != null){
            Retrofit retrofit = RetrofitClient.getInstance();
            authApi = retrofit.create(IAuthApi.class);
            authApi.login(model.getValue())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<ResponseApi<UserDetailResp>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(@NonNull ResponseApi<UserDetailResp> responseApi) {
                            Log.d(TAG, "onSuccess: "+responseApi.getData().toString());
                            if(responseApi.getStatus().equals(200)){
                                user = new User(responseApi.getData().getName(), responseApi.getData().getEmail(), responseApi.getAccessToken(), responseApi.getData().getAddress());
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
//

        }
    }

    public MutableLiveData<Boolean> getSuccess() {
        return _isSuccess;
    }

    public MutableLiveData<String> getErrMessage() {
        return _errMessage;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
