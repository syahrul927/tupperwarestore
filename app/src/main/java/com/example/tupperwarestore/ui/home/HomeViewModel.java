package com.example.tupperwarestore.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tupperwarestore.App;
import com.example.tupperwarestore.model.CategoryModel;
import com.example.tupperwarestore.model.ProductModel;
import com.example.tupperwarestore.model.resp.ProductResp;
import com.example.tupperwarestore.model.resp.ResponseApi;
import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.repository.UserDao;
import com.example.tupperwarestore.repository.UserDatabase;
import com.example.tupperwarestore.retrofit.IProductAPi;
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

import static android.content.ContentValues.TAG;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<User> userInfo = new MutableLiveData<User>(null);
    private MutableLiveData<List<CategoryModel>> category = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> isShowProduct = new MutableLiveData<Boolean>(false);
    private MutableLiveData<List<ProductModel>> products = new MutableLiveData<>(new ArrayList<>());


    private MutableLiveData<ProductModel> product = new MutableLiveData<>(new ProductModel());



    IProductAPi productAPi;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public HomeViewModel() {
    }

    public void getUser() {
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
    public void getListProduct() {
        Retrofit retrofit = RetrofitClient.getInstance();
        productAPi = retrofit.create(IProductAPi.class);
        productAPi.getListProduct().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseApi<List<ProductResp>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ResponseApi<List<ProductResp>> listResponseApi) {
                        List<ProductResp> prod = listResponseApi.getData();
                        List<ProductModel> list = new ArrayList<>();
                        List<CategoryModel> listCtg = new ArrayList<>();
                        List<Integer> ctgid = new ArrayList<>();
                        listCtg.add(new CategoryModel(0, "Semua", true));
                        for (ProductResp p :prod) {
                            ProductModel mod = new ProductModel(p.getId(), p.getName(), String.valueOf(p.getPrice()), p.getPict(), p.getDesc(), p.getVariant(),   p.getCategory() == null ? 0 : p.getCategory().getId() );
                            if(p.getCategory()!= null && !ctgid.contains(p.getCategory().getId())){
                                p.getCategory().setStatus(false);
                                listCtg.add(p.getCategory());
                                ctgid.add(p.getCategory().getId());
                            }
                            list.add(mod);
                        }
                        category.postValue(listCtg);
                        products.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });

    }
    public MutableLiveData<List<ProductModel>> getProducts() {
        return products;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    public MutableLiveData<ProductModel> getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product.setValue(product);
    }

    public MutableLiveData<List<CategoryModel>> getCategory() {
        return category;
    }

    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo.postValue(userInfo);
    }

    public MutableLiveData<Boolean> getIsShowProduct() {
        return isShowProduct;
    }

    public void setIsShowProduct(Boolean isShowProduct) {
        this.isShowProduct.setValue(isShowProduct);
    }


}