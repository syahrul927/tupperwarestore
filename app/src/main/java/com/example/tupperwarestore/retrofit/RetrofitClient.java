package com.example.tupperwarestore.retrofit;

import android.content.Context;
import android.util.Log;

import com.example.tupperwarestore.App;
import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.repository.UserDao;
import com.example.tupperwarestore.repository.UserDatabase;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final String TAG = "RetrofitClient";


    public static Retrofit getInstance(){
        if(instance == null){
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer "+ App.token.getValue())
                            .header("Authorization", "Bearer "+  App.token.getValue())
                            .method(original.method(), original.body());
                    Response response =  chain.proceed(request.build());
                    return response;
                }

            });
            OkHttpClient client = new OkHttpClient(httpClient);
            instance = new Retrofit.Builder()
                    .baseUrl("http://34.87.125.250:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return instance;
    }

    public RetrofitClient() {
    }
}
