package com.example.homework01.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.homework01.R;
import com.example.homework01.entity.User;
import com.example.homework01.fragment.ChatFragment;

public class MainActivity extends AppCompatActivity {

    final static String TAG = MainActivity.class.getSimpleName();
    public static final String BUNDLE_AVATAR = "avatar";
    public static final String BUNDLE_USER = "user";

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra(BUNDLE_AVATAR);
        User user = intent.getParcelableExtra(BUNDLE_USER);

        Log.i(TAG, "imageScale: " + String.format("{w: %d, h: %d}", bitmap.getWidth(), bitmap.getHeight()));

        toolbar = findViewById(R.id.sub_toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        RequestOptions requestOptions = new RequestOptions().override(120,120);

        Glide.with(this).load(new BitmapDrawable(null,bitmap)).fitCenter().apply(requestOptions).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                toolbar.setNavigationIcon(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ChatFragment chatFragment = new ChatFragment();

        fragmentTransaction.add(R.id.sub_frameLayout, chatFragment);
        fragmentTransaction.commit();
    }
}