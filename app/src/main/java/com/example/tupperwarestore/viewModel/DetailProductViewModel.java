package com.example.tupperwarestore.viewModel;

import android.content.Context;
import android.service.autofill.UserData;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.model.ProductModel;
import com.example.tupperwarestore.repository.Bag;
import com.example.tupperwarestore.repository.BagDao;
import com.example.tupperwarestore.repository.UserDatabase;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailProductViewModel extends ViewModel {

    private static final String TAG = "DetailProductViewModel";
    private MutableLiveData<Integer> selectedColor = new MutableLiveData<>();
    private BagDao dao = null;

    Bag bag = null;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<Integer> getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Integer selectedColor) {
        this.selectedColor.setValue(selectedColor);
    }

    public void saveBag(ProductModel model, Context context){
        try{
            dao = UserDatabase.getInstance(context).bagDao();

            dao.findById(model.getId()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MaybeObserver<Bag>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(@NonNull Bag bag2) {
                            Log.d(TAG, "onSuccess: result bag "+bag.toString());
                            if(bag2!=null){
                                bag = bag2;
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });
            if(bag == null) bag = new Bag();
            bag.setId(model.getId());
            bag.setName(model.getTitleProduct());
            bag.setPict(model.getUrl());
            int qty = bag.getQty() == null ? 1 : bag.getQty()+1;
            bag.setQty(qty);
            bag.setPrice(Double.valueOf(model.getPriceProduct()));
            dao.insert(bag).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: SUCCESS INSERT TO BAG");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e(TAG, "onError: FAILED INSERT", e);
                        }
                    });;
        }catch (Exception err){
            Log.e(TAG, "saveBag: Error",err );
        }

    }

    public DetailProductViewModel() {
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
