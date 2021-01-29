package com.example.tupperwarestore;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.repository.UserDao;
import com.example.tupperwarestore.repository.UserDatabase;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    public static App instance;
    private static Context dashboardContext;
    public static UserDatabase database;
    public static MutableLiveData<String> token = new MutableLiveData<>(null);
    public static MutableLiveData<User> userProfile = new MutableLiveData<>(null);
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static String TAG = "App";


    public static Context getDashboardContext(){
        return dashboardContext;
    }

    public static Context getContext(){
        return instance;
    }

    public static void updateToken(String tok){
        token.setValue(tok);
    }
    @Override
    public void onCreate() {
        dashboardContext = getApplicationContext();
        instance = this;
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
                    userProfile.postValue(user);
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

    public static void removeToken(){
        database = UserDatabase.getInstance(instance);
        UserDao dao = App.database.dao();
        dao.deleteAllUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Success Remove user");
                Runnable r = new Runnable() {
                    public void run() {
                        UserDatabase.getInstance(instance).clearAllTables();
                    }
                };
                new Thread(r).start();
                token.postValue(null);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

    }
}
