package com.example.tupperwarestore.ui.order;

import android.content.Context;
import android.service.autofill.UserData;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.model.CartRequest;
import com.example.tupperwarestore.model.CheckoutRequest;
import com.example.tupperwarestore.model.resp.ResponseApi;
import com.example.tupperwarestore.repository.Bag;
import com.example.tupperwarestore.repository.BagDao;
import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.repository.UserDatabase;
import com.example.tupperwarestore.retrofit.ICartApi;
import com.example.tupperwarestore.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrderViewModel extends ViewModel {

    private static final String TAG = "OrderViewModel";
    private MutableLiveData<List<Bag>> listBag = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> isSuccessCheckout = new MutableLiveData<>(false);
    ICartApi cartApi;
    private MutableLiveData<String> messageCheckout = new MutableLiveData<>();
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    Context context;


    private BagDao dao = null;

    public OrderViewModel() {
    }


    public void updateQtyCart(Bag model, Boolean increment){
        model.setQty(increment ? model.getQty()+1 : model.getQty()-1);
        dao = UserDatabase.getInstance(context).bagDao();
        if(model.getQty()>0){
            dao.insert(model).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete: Complete Update");
                    List<Bag> val = new ArrayList<>();
                    for (Bag b :
                            listBag.getValue()) {
                        if(b.getId().equals(model.getId())){
                            b.setQty(model.getQty());
                        }
                        val.add(b);
                    }
                    setListBag(val);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            });
        }else{
            dao.delete(model).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: Success delete last");
                            List<Bag> val = new ArrayList<>();
                            for (Bag b :
                                    listBag.getValue()) {
                                if(!b.getId().equals(model.getId())){
                                    val.add(b);
                                }
                            }
                            setListBag(val);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                            Log.e(TAG, "onComplete: Error while delete", e);
                        }
                    });
        }
    }

    public void checkoutProducts(){
        List<String> listIdProducts = new ArrayList<>();
        for (Bag bag :
                listBag.getValue()) {
            listIdProducts.add(bag.getId());
        }
        CheckoutRequest request = new CheckoutRequest(listIdProducts);
        Retrofit retrofit = RetrofitClient.getInstance();
        cartApi = retrofit.create(ICartApi.class);
        cartApi.checkoutCart(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseApi<CheckoutRequest>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ResponseApi<CheckoutRequest> listResponseApi) {
                        isSuccessCheckout.setValue(true);
                        dao = UserDatabase.getInstance(context).bagDao();
                        dao.deleteAllBags().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: Success Delete All Cart Local");
                                getListOfBag();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, "onError: Error when delete all cart local", e);
                            }
                        });

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isSuccessCheckout.setValue(false);
                        messageCheckout.setValue("Upss.. Gagal Saat Melakukan Checkout.. ");
                        Log.e(TAG, "onError: Error when checkout",e );
                    }
                });

    }

    public void getListOfBag(){
        try{
            dao = UserDatabase.getInstance(context).bagDao();
            dao.getAllBags().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Bag>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(@NonNull List<Bag> bags) {
                    if(bags.size()>0){
                        setListBag(bags);
                    }else{
                        Retrofit retrofit = RetrofitClient.getInstance();
                        cartApi = retrofit.create(ICartApi.class);
                        cartApi.getListCart().subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<ResponseApi<List<Bag>>>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        compositeDisposable.add(d);
                                    }

                                    @Override
                                    public void onSuccess(@NonNull ResponseApi<List<Bag>> listResponseApi) {
                                        Log.d(TAG, "onSuccess: Success  when get list cart from server");
                                        setListBag(listResponseApi.getData());
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.e(TAG, "onError: Error when get list cart from server", e);
                                    }
                                });
                    }
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

    public void updateCartServer(){
        List<CartRequest> listCart = new ArrayList<>();
        for (Bag bag: listBag.getValue()) {
            listCart.add(new CartRequest(bag.getId(), bag.getQty()));
        }

        Retrofit retrofit = RetrofitClient.getInstance();
        cartApi = retrofit.create(ICartApi.class);
        cartApi.updateCart(listCart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseApi<List<Bag>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseApi<List<Bag>> listResponseApi) {
                        Log.d(TAG, "onSuccess: Success update cart to server ");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: when try to update cart to server",e);
                    }
                });
    }

    public void setListBag(List<Bag> listBag) {
        this.listBag.setValue(listBag);
    }

    public void deleteItemCheckout(Bag model) {
        List<Bag> list = listBag.getValue();
        if(model !=null){
            dao = UserDatabase.getInstance(context).bagDao();
            dao.delete(model).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onComplete() {
                        for (int i = 0; i < list.size(); i++) {
                            if(list.get(i).getId().equals(model.getId())){
                               list.remove(i);
                            }
                        }
                        listBag.setValue(list);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            });
        }

    }


    public MutableLiveData<Boolean> getIsSuccessCheckout() {
        return isSuccessCheckout;
    }

    public void setIsSuccessCheckout(MutableLiveData<Boolean> isSuccessCheckout) {
        this.isSuccessCheckout = isSuccessCheckout;
    }

    public MutableLiveData<String> getMessageCheckout() {
        return messageCheckout;
    }

    public void setMessageCheckout(MutableLiveData<String> messageCheckout) {
        this.messageCheckout = messageCheckout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public MutableLiveData<List<Bag>> getListBag() {
        return listBag;
    }
}