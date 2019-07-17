package com.mobileapps.week04test;

import android.util.Log;


import com.mobileapps.week04test.models.PicturesResponse;
import com.mobileapps.week04test.retrofit.NetworkClient;
import com.mobileapps.week04test.retrofit.NetworkInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainPresenterInterface
{

    MainViewInterface mvi;
    private String TAG = "MainPresenter";

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    @Override
    public void getPictures()
    {
        Log.d("Heiner","Im going to get observale");
        getObservable().subscribeWith(getObserver());
        Log.d("Heiner","I Got observable");
    }

    //Create Observable TV
    public Observable<List<PicturesResponse>> getObservable(){
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getPictures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //Create Observer Me watching TV
    public DisposableObserver<List<PicturesResponse>> getObserver(){
        return new DisposableObserver<List<PicturesResponse>>() {

            @Override
            public void onNext(@NonNull List<PicturesResponse> pictureResponse) {
                Log.d(TAG,"OnNext"+pictureResponse.size());
                mvi.getPictures(pictureResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }


}