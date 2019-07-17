package com.mobileapps.week04test.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileapps.week04test.R;
import com.mobileapps.week04test.database.PictureDataBaseHelper;
import com.mobileapps.week04test.models.PicturesResponse;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class FullScreenActivity extends AppCompatActivity {

    PicturesResponse picture;
    PictureDataBaseHelper pictureDataBaseHelper;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        Intent intent = getIntent();
        id = intent.getIntExtra("id_picture",1);
        pictureDataBaseHelper = new PictureDataBaseHelper(this);
        picture = pictureDataBaseHelper.getPicture(String.valueOf(id));
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(picture.getTitle());
        ImageView imgFullScreen = findViewById(R.id.imgFullScreen);
        Picasso.with(this)
                .load(picture.getUrl())
                .into(imgFullScreen);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_screen_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_favorite);
        Log.d("Heiner","The image is "+picture.isFavorite());
        if(picture.isFavorite())
        {
            menuItem.setIcon(getDrawable(R.drawable.like_true));
        }
        else
        {
            menuItem.setIcon(getDrawable(R.drawable.like_false));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            if(picture.isFavorite())
            {
                item.setIcon(getDrawable(R.drawable.like_false));
                picture.setFavorite(false);
                Log.d("Heiner","before save is "+picture.isFavorite());
                pictureDataBaseHelper.updatePictureByID(picture);
                Log.d("Heiner","after save in DB is "+pictureDataBaseHelper.getPicture(String.valueOf(id)).getId());

                return true;
            }
            else
            {
                item.setIcon(getDrawable(R.drawable.like_true));
                picture.setFavorite(true);
                Log.d("Heiner","before save is "+picture.isFavorite());
                pictureDataBaseHelper.updatePictureByID(picture);
                Log.d("Heiner","after save in DB is "+pictureDataBaseHelper.getPicture(String.valueOf(id)).getId());

                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }

}
