package com.aariyan.composite_disposable.Interface;

import com.aariyan.composite_disposable.Model.POJO;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apis {

    @GET("posts")
    Observable<ResponseBody> getPost();
}
