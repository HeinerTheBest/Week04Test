package com.mobileapps.week04test.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import com.mobileapps.week04test.MainPresenter;
import com.mobileapps.week04test.MainViewInterface;
import com.mobileapps.week04test.R;
import com.mobileapps.week04test.adapter.ImagesAdapter;
import com.mobileapps.week04test.database.PictureDataBaseHelper;
import com.mobileapps.week04test.models.PicturesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainViewInterface
{

    @BindView(R.id.rvPicture)
    RecyclerView rvPictures;

    private String TAG = "MainActivity";
    RecyclerView.Adapter adapter;
    MainPresenter mainPresenter;
    PictureDataBaseHelper pictureDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        //rvPictures = findViewById(R.id.rvPicture);

        Log.d("Heiner","Im in the main actvity");
        pictureDataBaseHelper = new PictureDataBaseHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Heiner","onResume");
        setupMVP();
        setupViews();
        getMovieList();
    }

    private void setupMVP()
    {
        mainPresenter = new MainPresenter(this);
    }

    private void setupViews()
    {
        rvPictures.setLayoutManager(new GridLayoutManager(this,2));
    }

    private void getMovieList()
    {
        mainPresenter.getPictures();

    }


    @Override
    public void getPictures(List<PicturesResponse> pictures)
    {
        if(pictures!=null) {
            Log.d(TAG,pictures.get(0).getTitle());
            adapter = new ImagesAdapter(pictures, MainActivity.this);
            rvPictures.setAdapter(adapter);
            if(pictureDataBaseHelper.count() == 0) {
                Log.d("Heiner ","Creating dataBase");
                for (PicturesResponse picture : pictures) {
                    pictureDataBaseHelper.insertPicture(picture);
                }
            }

        }else{
            Log.d(TAG,"Movies response null");
        }
    }

    @Override
    public void displayError(String s) {

    }
}