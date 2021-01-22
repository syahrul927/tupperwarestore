package com.example.tupperwarestore.ui.order;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.repository.Bag;
import com.example.tupperwarestore.repository.BagDao;
import com.example.tupperwarestore.repository.UserDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderViewModel extends ViewModel {

    private static final String TAG = "OrderViewModel";
    private MutableLiveData<List<Bag>> listBag = new MutableLiveData<>(new ArrayList<>());

    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BagDao dao = null;
    public OrderViewModel() {
    }


    public MutableLiveData<List<Bag>> getListBag() {
        return listBag;
    }

    public void getListOfBag(Context context){
        try{
            dao = UserDatabase.getInstance(context).bagDao();
            dao.getAllBags().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Bag>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(@NonNull List<Bag> bags) {
                    setListBag(bags);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            });
        }catch (Exception err){
            Log.e(TAG, "getListOfBag: Error", err);
        }

    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    public void setListBag(List<Bag> listBag) {
        this.listBag.setValue(listBag);
    }
}