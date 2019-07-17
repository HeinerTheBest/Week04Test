package com.mobileapps.week04test;

import com.mobileapps.week04test.models.PicturesResponse;

import java.util.List;

public interface MainViewInterface
{
    void getPictures(List<PicturesResponse> pictures);
    void displayError(String s);
}
