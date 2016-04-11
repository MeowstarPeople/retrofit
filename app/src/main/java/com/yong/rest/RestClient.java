package com.yong.rest;

import com.yong.bean.BeanBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Url;


public interface RestClient {

    @FormUrlEncoded
    @POST("joke_text")
    Call<RestBean<BeanBody>> getJoke(@Header("apikey") String key, @FieldMap Map<String, String> fields);

    /**
     * 动态设置URL，用GET方式，注意导包
     *
     * @param url
     * @return
     */
    @GET
    Call<String> getbaidu(@Url String url);

}
