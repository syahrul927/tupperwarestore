package com.example.tupperwarestore.ui.notifications;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.repository.UserDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<Boolean> logout = new MutableLiveData<>(false);




    public NotificationsViewModel() {
    }

    public MutableLiveData<Boolean> getLogout() {
        return logout;
    }

    public void setLogout(MutableLiveData<Boolean> logout) {
        this.logout = logout;
    }

    public void logout(Context context){
        logout.setValue(true);

    }
}