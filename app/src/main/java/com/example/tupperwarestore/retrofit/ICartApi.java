package com.example.tupperwarestore.retrofit;

import com.example.tupperwarestore.model.CartRequest;
import com.example.tupperwarestore.model.CheckoutRequest;
import com.example.tupperwarestore.model.resp.ProductResp;
import com.example.tupperwarestore.model.resp.ResponseApi;
import com.example.tupperwarestore.repository.Bag;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ICartApi {
    @GET("cart")
    Single<ResponseApi<List<Bag>>> getListCart();

    @POST("cart")
    Single<ResponseApi<List<Bag>>> updateCart(@Body List<CartRequest> cart);

    @POST("checkout")
    Single<ResponseApi<CheckoutRequest>> checkoutCart(@Body CheckoutRequest checkout);


}
