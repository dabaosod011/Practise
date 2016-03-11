package com.markeloff.practise.Retrofit.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hai Xiao on 2016/3/7.
 */
public interface IApiMethods {
    @GET("get/curators.json")
    Call<Curator> getCurators(@Query("api_key") String api_key);

}
