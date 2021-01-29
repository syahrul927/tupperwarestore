package com.example.tupperwarestore.ui.notifications;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.App;
import com.example.tupperwarestore.repository.BagDao;
import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.repository.UserDao;
import com.example.tupperwarestore.repository.UserDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<Boolean> logout = new MutableLiveData<>(false);
    private MutableLiveData<User> userInfo = new MutableLiveData<User>(null);
    private static String TAG = "NotificationsViewModel";

    CompositeDisposable compositeDisposable = new CompositeDisposable();


    public NotificationsViewModel() {
    }

    public MutableLiveData<Boolean> getLogout() {
        return logout;
    }
    public void getUserData() {
        UserDao dao = App.database.dao();
        dao.getAllUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull List<User> users) {
                if(users.size()>0){
                    User user = users.get(0);
                    userInfo.postValue(user);

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("INIT", "onError: ",e );
            }
        });

    }

    public void setLogout(MutableLiveData<Boolean> logout) {
        this.logout = logout;
    }

    public void logout(Context context){
        App.removeToken();
        logout.setValue(true);

    }


    public MutableLiveData<User> getUser() {
        return userInfo;
    }

    public void setUser(MutableLiveData<User> user) {
        this.userInfo = user;
    }
}