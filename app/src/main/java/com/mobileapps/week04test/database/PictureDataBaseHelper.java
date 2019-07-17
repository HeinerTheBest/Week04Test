package com.mobileapps.week04test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobileapps.week04test.models.PicturesResponse;

import java.util.ArrayList;
import java.util.List;

import static com.mobileapps.week04test.database.PictureDataBaseContract.COLUMN_ALBUM_ID;
import static com.mobileapps.week04test.database.PictureDataBaseContract.COLUMN_FAVORITE;
import static com.mobileapps.week04test.database.PictureDataBaseContract.COLUMN_ID;
import static com.mobileapps.week04test.database.PictureDataBaseContract.COLUMN_THUMB_URL;
import static com.mobileapps.week04test.database.PictureDataBaseContract.COLUMN_TITLE;
import static com.mobileapps.week04test.database.PictureDataBaseContract.COLUMN_URL;
import static com.mobileapps.week04test.database.PictureDataBaseContract.DATABASE_NAME;
import static com.mobileapps.week04test.database.PictureDataBaseContract.DATABASE_VERSION;
import static com.mobileapps.week04test.database.PictureDataBaseContract.PICTURE_TABLE_CREATE;
import static com.mobileapps.week04test.database.PictureDataBaseContract.TABLE_NAME;

public class PictureDataBaseHelper extends SQLiteOpenHelper
{
    String TAG = "dbHelper";

    public PictureDataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"Creating the table ");
        db.execSQL(PICTURE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.d(TAG,"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


    public long insertPicture(PicturesResponse picture)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,         picture.getId());
        contentValues.put(COLUMN_ALBUM_ID,   picture.getAlbumId());
        contentValues.put(COLUMN_TITLE,      picture.getTitle());
        contentValues.put(COLUMN_URL,        picture.getUrl());
        contentValues.put(COLUMN_THUMB_URL,  picture.getThumbnailUrl());
        contentValues.put(COLUMN_FAVORITE,   picture.isFavorite());

        final long id = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return id;
    }


    public List<PicturesResponse> getAllPicture() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<PicturesResponse> returnList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Integer  id         =                      cursor.getInt(cursor.getColumnIndex(COLUMN_ID))                ;
                Integer  albumId    =                      cursor.getInt(cursor.getColumnIndex(COLUMN_ALBUM_ID))          ;
                String   title      =                      cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))          ;
                String   url        =                      cursor.getString(cursor.getColumnIndex(COLUMN_URL))            ;
                String   thumb_url  =                      cursor.getString(cursor.getColumnIndex(COLUMN_THUMB_URL))      ;
                boolean  isFavorite = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE)))      ;

                returnList.add(new PicturesResponse(id, albumId,title,url,thumb_url,isFavorite));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return returnList;
    }



    public PicturesResponse getPicture(String idToFInd) {

        SQLiteDatabase database = this.getReadableDatabase();
        PicturesResponse returnPicture = new PicturesResponse();

        Cursor cursor = database.rawQuery(PictureDataBaseContract.getById(idToFInd),null);

        if (cursor.moveToFirst()) {

            Integer  id         =                      cursor.getInt(cursor.getColumnIndex(COLUMN_ID))                ;
            Integer  albumId    =                      cursor.getInt(cursor.getColumnIndex(COLUMN_ALBUM_ID))          ;
            String   title      =                      cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))          ;
            String   url        =                      cursor.getString(cursor.getColumnIndex(COLUMN_URL))            ;
            String   thumb_url  =                      cursor.getString(cursor.getColumnIndex(COLUMN_THUMB_URL))      ;
            boolean  isFavorite = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITE)))      ;
            returnPicture = new PicturesResponse(id, albumId,title,url,thumb_url,isFavorite);
        }
        cursor.close();
        database.close();
        return returnPicture;
    }

    public int updatePictureByID(PicturesResponse picture) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID,         picture.getId());
        contentValues.put(COLUMN_ALBUM_ID,   picture.getAlbumId());
        contentValues.put(COLUMN_TITLE,      picture.getTitle());
        contentValues.put(COLUMN_URL,        picture.getUrl());
        contentValues.put(COLUMN_THUMB_URL,  picture.getThumbnailUrl());
        contentValues.put(COLUMN_FAVORITE,   String.valueOf(picture.isFavorite()));


        final int numberOfRowsUpdated = database.update(TABLE_NAME,
                contentValues, // new values to insert
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(picture.getId())});

        database.close();

        return numberOfRowsUpdated;
    }

    //Count
    public long count()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        final long count = DatabaseUtils.queryNumEntries(database, TABLE_NAME);
        database.close();
        return count;
    }







}
