package com.example.homework01.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.homework01.entity.User;
import com.example.homework01.utils.ImageHelper;
import com.example.homework01.utils.SQLiteCRUDHelper;

public class UserDao {

    private Context context;

    public UserDao(Context context) {
        this.context = context;
    }

    public User getUser(String username){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        User user = null;

        try {
            db = UserSQLiteOpenHelper.getReadableDatabase(context);

            if (db.isOpen()) {
                cursor = db.query(UserSQLiteOpenHelper.USER_TB_NAME,
                        new String[]{"*"},
                        User.COLUMN_USERNAME + "=?", new String[]{username},
                        null, null, null);
                // unique
                if (cursor.moveToFirst()) {
                    user = User.getUser(cursor);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            SQLiteCRUDHelper.close(db, cursor);
        }
        // TODO
        return user;
    }


    public String getPassword(User user) {
        return getPassword(user.getUsername());
    }

    public String getPassword(String username) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String passowrd = null;

        try {
            db = UserSQLiteOpenHelper.getReadableDatabase(context);

            if (db.isOpen()) {
                cursor = db.query(UserSQLiteOpenHelper.USER_TB_NAME,
                        new String[]{User.COLUMN_PASSWORD},
                        User.COLUMN_USERNAME + "=?", new String[]{username},
                        null, null, null);
                // unique
                if (cursor.moveToFirst()) {
                    passowrd = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_PASSWORD));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            SQLiteCRUDHelper.close(db, cursor);
        }
        return passowrd;
    }

    public String getAvatar(String username) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String avatar = null;

        try {
            db = UserSQLiteOpenHelper.getReadableDatabase(context);

            if (db.isOpen()) {
                cursor = db.query(UserSQLiteOpenHelper.USER_TB_NAME,
                        new String[]{User.COLUMN_AVATAR},
                        User.COLUMN_USERNAME + "=?", new String[]{username},
                        null, null, null);
                // unique
                if (cursor.moveToFirst()) {
                    avatar = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_AVATAR));
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            SQLiteCRUDHelper.close(db, cursor);
        }
        return avatar;
    }

    public boolean isUserExisting(String username){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = UserSQLiteOpenHelper.getReadableDatabase(context);

            if (db.isOpen()) {
                cursor = db.query(UserSQLiteOpenHelper.USER_TB_NAME,
                        new String[]{"1"},
                        User.COLUMN_USERNAME + "=?", new String[]{username},
                        null, null, null);
                // unique
                if (cursor.moveToFirst()) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            SQLiteCRUDHelper.close(db, cursor);
        }

        return false;
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = null;
        ContentValues contentValues = new ContentValues();
        user.putContentValues(contentValues);

        try {
            db = UserSQLiteOpenHelper.getWritableDatabase(context);

            if (db.isOpen()) {
                db.insertOrThrow(UserSQLiteOpenHelper.USER_TB_NAME, User.COLUMN_GENDER, contentValues);
            }
        } catch (SQLException e) {
            return false;
        } finally {
            SQLiteCRUDHelper.close(db, null);
        }

        return true;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = null;
        ContentValues contentValues = new ContentValues();
        user.putContentValues(contentValues);
        // TODO: isValid
        contentValues.remove(User.COLUMN_USERNAME);
        int row = 0;

        try {
            db = UserSQLiteOpenHelper.getWritableDatabase(context);

            if (db.isOpen()) {
                row = db.update(UserSQLiteOpenHelper.USER_TB_NAME, contentValues, User.COLUMN_USERNAME + "=?", new String[]{user.getUsername()});
            }
        } catch (SQLException e) {
            return false;
        } finally {
            SQLiteCRUDHelper.close(db, null);
        }

        return row == 0 ? false : true;
    }

    public boolean deleteUserAndAvatar(User user){
        if(deleteUser(user)){
            if(ImageHelper.deleteImgInApp("root.png")){
                // TODO:
            }
            return true;
        }
        return false;
    }

    private boolean deleteUser(User user) {
        return deleteUser(user.getUsername());
    }

    private boolean deleteUser(String username) {
        SQLiteDatabase db = null;
        int row = 0;

        try {
            db = UserSQLiteOpenHelper.getWritableDatabase(context);

            if (db.isOpen()) {
                row = db.delete(UserSQLiteOpenHelper.USER_TB_NAME, User.COLUMN_USERNAME + "=?", new String[]{username});
            }
        } catch (SQLException e) {
            return false;
        } finally {
            SQLiteCRUDHelper.close(db, null);
        }

        return row == 0 ? false : true;
    }

    public int getNumberOfNone(){
        return getNumberOfOneGender(User.GENDER_NONE);
    }
    public int getNumberOfFemale(){
        return getNumberOfOneGender(User.GENDER_FEMALE);
    }
    public int getNumberOfMale(){
        return getNumberOfOneGender(User.GENDER_MALE);
    }

    public int getNumberOfOneGender(Integer gender){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int count = -1;

        try {
            db = UserSQLiteOpenHelper.getReadableDatabase(context);

            if (db.isOpen()) {
                if(gender==null){
                    cursor = db.query(UserSQLiteOpenHelper.USER_TB_NAME,
                            new String[]{"count(1)"}, User.COLUMN_GENDER+" is null",
                            null, null, null, null);
                }else{
                    cursor = db.query(UserSQLiteOpenHelper.USER_TB_NAME,
                            new String[]{"count(1)"}, User.COLUMN_GENDER+"=?",
                            new String[]{gender.toString()},
                            null, null, null);
                }
                // unique
                if (cursor.moveToFirst()) {
                    count = cursor.getInt(0);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            SQLiteCRUDHelper.close(db, cursor);
        }
        return count;
    }
}
