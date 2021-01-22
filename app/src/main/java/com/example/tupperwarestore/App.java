package com.example.tupperwarestore;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.repository.UserDao;
import com.example.tupperwarestore.repository.UserDatabase;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    public static UserDatabase database;
    public static MutableLiveData<String> token = new MutableLiveData<>();
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static void updateToken(String tok){
        token.setValue(tok);
    }
    @Override
    public void onCreate() {

        database = UserDatabase.getInstance(this);
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
                     token.setValue(user.getToken());

                }
                compositeDisposable.clear();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("INIT", "onError: ",e );
            }
        });
        super.onCreate();

    }
}
