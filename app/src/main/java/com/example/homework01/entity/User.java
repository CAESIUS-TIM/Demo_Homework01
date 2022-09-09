package com.example.homework01.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.homework01.utils.SQLiteCRUDHelper;

import java.io.Serializable;


public class User implements Serializable {
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_USERNAME = "username";
    public final static String COLUMN_PASSWORD = "password";
    public final static String COLUMN_AVATAR = "avatar";
    public final static String COLUMN_GENDER = "gender";
    public final static String COLUMN_BIRTHDAY = "birthday";
    public final static String COLUMN_ADDRESS = "address";
    public final static String COLUMN_EMAIL = "emial";
    public final static String COLUMN_JOB = "job";
    public final static String COLUMN_COMPANY = "company";

    public final static Integer GENDER_NONE = null;
    public final static Integer GENDER_FEMALE = 0;
    public final static Integer GENDER_MALE = 1;

    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private Integer gender;
    private Integer birthday;
    private String address;
    private String email;
    private String job;
    private String company;

    public User() {
    }

    public User(String username, String password, String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + (gender == GENDER_NONE ? "none" : (gender == GENDER_FEMALE ? "female" : "male")) +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", job='" + job + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    public ContentValues putContentValues(ContentValues contentValues) {
        // TODO: id ???
        // TODO: avatar file saver

        contentValues.put(COLUMN_AVATAR, avatar);
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_BIRTHDAY, birthday);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_JOB, job);
        contentValues.put(COLUMN_COMPANY, company);
        return contentValues;
    }

    public static User getUser(Cursor cursor) {
        User user = new User();
        // @NonNull
        user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)));
        user.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR)));
        // @Nullable
        // Integer
        user.setGender(SQLiteCRUDHelper.getNullableInteger(cursor,COLUMN_GENDER));
        user.setBirthday(SQLiteCRUDHelper.getNullableInteger(cursor,COLUMN_BIRTHDAY));
        // String
        user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
        user.setJob(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOB)));
        user.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPANY)));
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
