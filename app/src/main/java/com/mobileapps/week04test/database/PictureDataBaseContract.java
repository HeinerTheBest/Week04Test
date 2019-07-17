package com.mobileapps.week04test.database;

public class PictureDataBaseContract
{
    public static final String DATABASE_NAME    = "picture_db";
    public static final int DATABASE_VERSION    = 1;
    public static final String TABLE_NAME       = "picture_table";
    public static final String COLUMN_ALBUM_ID  = "albumId";
    public static final String COLUMN_ID        = "id";
    public static final String COLUMN_TITLE     = "title";
    public static final String COLUMN_URL       = "url";
    public static final String COLUMN_THUMB_URL = "thumbnailUrl";
    public static final String COLUMN_FAVORITE  = "favorite";

    public static final String DROP_QUERY =String.format("DROP TABLE %S", TABLE_NAME);

    public static final String SELECT_ALL_QUERY = String.format("SELECT * FROM %s", TABLE_NAME);

    public static final String PICTURE_TABLE_CREATE =
            "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    COLUMN_ID             + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    COLUMN_ALBUM_ID       + " INTEGER, " +
                    COLUMN_TITLE          + " TEXT, " +
                    COLUMN_URL            + " TEXT, " +
                    COLUMN_THUMB_URL      + " TEXT, " +
                    COLUMN_FAVORITE       + " TEXT " +
                    ");";

    public static  String getIfIsFavorite()
    {
        return String.format("%s WHERE %S = \"%s\"",
                SELECT_ALL_QUERY,COLUMN_FAVORITE,"true");
    }

    public static  String getById(String id)
    {
        return String.format("%s WHERE %S = \"%s\"",
                SELECT_ALL_QUERY,COLUMN_ID,id);
    }




}
