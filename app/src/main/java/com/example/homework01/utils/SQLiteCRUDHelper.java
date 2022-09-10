package com.example.homework01.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class SQLiteCRUDHelper {
    
    public static void close(@Nullable SQLiteDatabase db,@Nullable Cursor cursor){
        if(db!=null){
            db.close();
        }
        if(cursor!=null){
            cursor.close();
        }
    }

    public static Integer getNullableInteger(Cursor cursor, int columnIndex){
        return cursor.isNull(columnIndex) ? null : cursor.getInt(columnIndex);
    }

    public static Integer getNullableInteger(Cursor cursor, String columnName){
        return getNullableInteger(cursor, cursor.getColumnIndexOrThrow(columnName));
    }
}
