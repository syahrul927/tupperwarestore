package com.example.tupperwarestore.retrofit;


import com.example.tupperwarestore.model.LoginModel;
import com.example.tupperwarestore.model.RegisterModel;
import com.example.tupperwarestore.model.resp.ResponseApi;
import com.example.tupperwarestore.model.resp.UserDetailResp;


import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthApi {

    @POST("login")
    Single<ResponseApi<UserDetailResp>> login(@Body LoginModel model);
    @POST("register")
    Single<ResponseApi> register(@Body RegisterModel model);

}
