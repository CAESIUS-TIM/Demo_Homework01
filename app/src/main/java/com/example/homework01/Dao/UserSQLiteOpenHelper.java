package com.example.homework01.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.homework01.entity.User;

/**
 * {@link UserSQLiteOpenHelper} Utils Class<br/>
 * Only one instance<br/>
 * 1. private constructor {@link UserSQLiteOpenHelper#UserSQLiteOpenHelper(Context, String, SQLiteDatabase.CursorFactory, int)}<br/>
 * 2. use {@link UserSQLiteOpenHelper#getmInstance(Context)} to get instance
 */
public class UserSQLiteOpenHelper extends SQLiteOpenHelper {

    private final static String TAG = UserSQLiteOpenHelper.class.getSimpleName();

    public final static String DB_NAME = "homework01.db";
    public final static String USER_TB_NAME = "users";
    // use `_id` as primary key
    public final static String CREATE_TABLE_SQL =
            "CREATE TABLE " + USER_TB_NAME + "(" +
                    User.COLUMN_ID + " " +          "INTEGER PRIMARY Key AUTOINCREMENT," +
                    User.COLUMN_USERNAME + " " +    "TEXT    NOT NULL    UNIQUE," +
                    User.COLUMN_PASSWORD + " " +    "TEXT    NOT NULL," +
                    User.COLUMN_AVATAR + " " +      "BLOB," +
                    User.COLUMN_GENDER + " " +      "INTEGER CHECK(gender IN (NULL,0,1))," + // NULL, MALE, FEMALE
                    User.COLUMN_BIRTHDAY + " " +    "INTEGER," + // Julian Day
                    User.COLUMN_EMAIL + " " +   "TEXT," +
                    User.COLUMN_ADDRESS + " " + "TEXT," +
                    User.COLUMN_JOB + " " + "TEXT," +
                    User.COLUMN_COMPANY + " " + "TEXT" +
                    ")";

    private static SQLiteOpenHelper mInstance;

    public static synchronized SQLiteOpenHelper getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserSQLiteOpenHelper(context, DB_NAME, null, 1);
        }
        return mInstance;
    }

    public static SQLiteDatabase getReadableDatabase(Context context) {
        return getmInstance(context).getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        return getmInstance(context).getWritableDatabase();
    }

    private UserSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: " + CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
