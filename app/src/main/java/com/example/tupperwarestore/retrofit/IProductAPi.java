package com.example.tupperwarestore.retrofit;

import com.example.tupperwarestore.model.resp.ProductResp;
import com.example.tupperwarestore.model.resp.ResponseApi;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface IProductAPi {

    @GET("tupperware")
    Single<ResponseApi<List<ProductResp>>> getListProduct();
}
