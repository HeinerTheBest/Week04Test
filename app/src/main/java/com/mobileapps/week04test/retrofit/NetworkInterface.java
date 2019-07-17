package com.mobileapps.week04test.retrofit;



import com.mobileapps.week04test.models.PicturesResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.mobileapps.week04test.retrofit.RetrofitConstant.PATH;


public interface NetworkInterface
{
    @GET(PATH)
    Observable<List<PicturesResponse>> getPictures();

}