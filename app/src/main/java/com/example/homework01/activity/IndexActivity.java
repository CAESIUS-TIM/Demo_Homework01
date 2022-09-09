package com.example.homework01.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.example.homework01.Dao.UserDao;
import com.example.homework01.R;
import com.example.homework01.fragment.RegisterFragment;
import com.example.homework01.fragment.LoginFragment;
import com.example.homework01.impl.InitiatedViewer;
import com.example.homework01.utils.ImageHelper;

/**
 * {@link IndexActivity} is the first activity you will see when enter the activity, TODO: unless you have signed in.
 * there are 3 fragments (login, create, forgot)
 * <p>TODO: No confusion! use (login, register and forgot), same as <a href="https://kog.tw">kog.tw</a>.
 */
public class IndexActivity extends AppCompatActivity implements View.OnClickListener, InitiatedViewer {

    // Common
    final static String TAG = IndexActivity.class.getSimpleName();
    private UserDao userDao;

    // Component
    private ImageView iv_play;

    // Context relative
    private FragmentManager fragmentManager;

    // Misc
    /**
     * 从图像随机生成网站上定时爬取图片（为了图片的更新，所以不能缓存）并显示在 Activity 中，因此
     * 可以在所有 Fragments 被看到，增加趣味性；并且作为 Create 中可选择的头像（Create 就可以从前者或者相册中选择头像）。
     * <p>{@link IndexActivity#iv_play}获取图片数据和显示是几乎同步，但是仍可能在极端情况出现问题。
     * <p>传输加载图片时，可能因为 scaleType 改变图片大小，所以要及时 override size，
     * 不然就可能在 intent 传递数据时发生数据过大的错误。
     * <p> 在切换 Activity （如 {@link Fragment#startActivity(Intent)}）时发生的错误可能会被丢失，
     * 导致仅有闪退而没有报错。
     */
    public final static int MSG_IV_PLAY = 100;
    public final static int TRANSITION_DURATION = 1000;

    private final static DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory
            .Builder(TRANSITION_DURATION).setCrossFadeEnabled(true).build();

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.i(TAG, "handleMessage: ");
            switch (msg.what) {
                case MSG_IV_PLAY:
                    changeImageViewPlay();
                    break;
            }
            return true;
        }
    });

    /**
     * crawl pictures from random picture generator website as decorations and avatars for create a new account.
     * view: {@link IndexActivity#iv_play}
     * url: R.string.randomCatUrl
     * TODO: url in "@link" formation
     */
    private void changeImageViewPlay() {
        try {
            Glide.with(IndexActivity.this).load(getString(R.string.randomCatUrl))
//                .placeholder(currentDrawable) // 原来可能是浅拷贝，导致 transition 是同一张图的动画
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 取消 url 缓存
                    .skipMemoryCache(true) // 取消 url 缓存
                    .apply(ImageHelper.avatarOptions)
//                .transition(DrawableTransitionOptions.withCrossFade(factory)) // the anim of load(url) is not recommended by office.
                    .transform(new CircleCrop())
                    .fitCenter() // may change the size of the image
                    .into(new CustomTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    Log.i(TAG, "onResourceReady: " + String.format("{resource: %s, transition: %s", resource, transition));
                                    iv_play.setImageDrawable(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                    Log.i(TAG, "onLoadCleared: ");
                                }
                            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        // Common
        userDao = new UserDao(this);

        fragmentManager = getSupportFragmentManager();
        /** {@link FragmentTransaction} only can be committed once. */
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frameLayout, getLoginFragment());
        fragmentTransaction.commit();

        findViews();
        setViews();
        runSubThread();
    }

    @Override
    public void findViews() {
        iv_play = findViewById(R.id.main_imageView_play);
    }

    @Override
    public void setViews() {
        iv_play.setOnClickListener(this);
    }

    private void runSubThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(MSG_IV_PLAY);
                }
            }
        }).start();
    }

    private LoginFragment getLoginFragment() {
        if (fragmentManager == null || userDao == null) {
            throw new NullPointerException(IndexActivity.class.getSimpleName() + " hasn't initiated");
        }
        return new LoginFragment(this, fragmentManager, userDao);
    }

    private RegisterFragment getCreateFragment() {
        if (fragmentManager == null || userDao == null) {
            throw new NullPointerException(IndexActivity.class.getSimpleName() + " hasn't initiated");
        }
        return new RegisterFragment(this, fragmentManager, userDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_imageView_play:
                Fragment fragment = fragmentManager.findFragmentById(R.id.main_frameLayout);
                if (RegisterFragment.class.isInstance(fragment)) {
                    ((RegisterFragment) fragment).setAvatar(iv_play.getDrawable());
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_frameLayout);
        if (RegisterFragment.class.isInstance(currentFragment)) {
            RegisterFragment registerFragment = (RegisterFragment) currentFragment;
            if(registerFragment.mSelectPicturePopupWindow != null){
                registerFragment.mSelectPicturePopupWindow.dismissPopupWindow();
            }else {
                fragmentManager.beginTransaction().replace(R.id.main_frameLayout, new LoginFragment(this, fragmentManager, userDao)).commit();
            }
        } else {
            super.onBackPressed();
        }
    }
}