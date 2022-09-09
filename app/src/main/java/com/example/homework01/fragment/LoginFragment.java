package com.example.homework01.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.PathUtils;
import com.bumptech.glide.Glide;
import com.example.homework01.Dao.UserDao;
import com.example.homework01.R;
import com.example.homework01.activity.MainActivity;
import com.example.homework01.entity.User;
import com.example.homework01.impl.NeedAllFragment;
import com.example.homework01.utils.WebBrowser;

import java.nio.file.Paths;
import java.util.Objects;

public class LoginFragment extends NeedAllFragment implements View.OnClickListener {
    // Common
    final static String TAG = LoginFragment.class.getSimpleName();
    final static String BUNDLE_AVATAR = "BUNDLE_AVATAR";
    final static String BUNDLE_USERNAME = "BUNDLE_USERNAME";
    final static String BUNDLE_PASSWORD = "BUNDLE_PASSWORD";

    // Component
    private LinearLayout ll_main, ll_agree;
    private EditText et_username, et_password;
    private Button bt_signIn, bt_forgot, bt_create;
    private RadioButton rb_agree;
    private TextView tv_agree;
    private ImageView iv_avatar;

    // Misc
    SpringAnimation ll_agree_springAnimation;
    private boolean agree = false;

    public LoginFragment(AppCompatActivity activityContext, FragmentManager fragmentManager, UserDao userDao) {
        super(activityContext, fragmentManager, userDao);
    }

    public void setAvatar(Drawable drawable) {
        iv_avatar.setImageDrawable(drawable);
    }

    public void setAvatar(Bitmap bitmap) {
        iv_avatar.setImageBitmap(bitmap);
    }

    public void setUsername(String username) {
        et_username.setText(username);
    }

    public void setPassword(String password) {
        et_password.setText(password);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null) {
            Log.i(TAG, "onCreateView: root is creating");
            root = inflater.inflate(R.layout.fragment_login, container, false);
        }

        initView();

        return root;
    }

    @Override
    public void findViews() {
        ll_main = root.findViewById(R.id.login_linearLayout_main);
        ll_agree = root.findViewById(R.id.login_linearLayout_agree);
        et_username = root.findViewById(R.id.login_editText_username);
        et_password = root.findViewById(R.id.login_editText_password);
        bt_signIn = root.findViewById(R.id.login_button_signIn);
        bt_forgot = root.findViewById(R.id.login_button_forgot);
        bt_create = root.findViewById(R.id.login_button_create);
        rb_agree = root.findViewById(R.id.login_radioButton_agree);
        tv_agree = root.findViewById(R.id.login_textView_agreeHref);
        iv_avatar = root.findViewById(R.id.login_imageView_avatar);
    }

    @Override
    public void setViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Bitmap avatar = bundle.getParcelable(BUNDLE_AVATAR);
            String username = bundle.getString(BUNDLE_USERNAME);
            String password = bundle.getString(BUNDLE_PASSWORD);
            Log.i(TAG, "initViews: " + avatar + ", " + username + ", " + password);
            iv_avatar.setImageBitmap(avatar);
            et_username.setText(username);
            et_password.setText(password);
        }

        rb_agree.setOnClickListener(this);
        bt_signIn.setOnClickListener(this);
        bt_forgot.setOnClickListener(this);
        bt_create.setOnClickListener(this);
        tv_agree.setOnClickListener(this);

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = et_username.getText().toString();
                String avatar = userDao.getAvatar(username);
                if (avatar != null) {
                    Log.i(TAG, "onTextChanged: user " + username + " exists");
                    // TODO: image is unready but already redirect activity.
                    Glide.with(root.getContext())
                            .load(Paths.get(PathUtils.getExternalAppDcimPath(), getString(R.string.avatar), username + ".png").toFile())
                            .override(180, 180)
                            .into(iv_avatar);
                } else {
                    Log.i(TAG, "onTextChanged: user " + username + " doesn't exist");
                    iv_avatar.setImageResource(R.drawable.gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ll_agree_springAnimation = new SpringAnimation(ll_agree, DynamicAnimation.X, 0);
        ll_agree_springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_radioButton_agree:
                Log.i(TAG, "onClick: agree = " + agree);
                toggleAgree();
                break;

            case R.id.login_button_signIn:
                login();
                break;

            case R.id.login_button_forgot:
                // TODO
                Toast.makeText(activity, "Forgot", Toast.LENGTH_SHORT).show();
                break;

            case R.id.login_button_create:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frameLayout, new RegisterFragment(activity, fragmentManager, userDao));
                fragmentTransaction.commit();
                break;

            case R.id.login_textView_agreeHref:
                WebBrowser.openBrowser(this.getContext(), getString(R.string.memo_href));
                break;

            default:
                Log.e(TAG, "onClick: " + String.format("unbind id {%d}", v.getId()));
        }

    }

    private void replaceWithCreateFragment(){

    }

    private void toggleAgree() {
        if (agree) {
            rb_agree.setChecked(agree = false);
        } else {
            agree = true;
        }
    }

    private void login() {
        // TODO
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        // TODO: dangerous test
        Log.i(TAG, "onClick: " + String.format("{username: \"%s\", password: \"%s\", agree: \"%s\"}", username, password, agree ? "True" : "False"));
        if (!agree) {
            ll_agree_springAnimation.setStartValue(30);
            ll_agree_springAnimation.start();
            Toast.makeText(activity, getString(R.string.login_agree), Toast.LENGTH_SHORT).show();
        } else if (username.equals("") || password.equals("")) {
            // @NonNull username, @NonNull password, no need to ask database for checking.
            Toast.makeText(activity, getString(R.string.login_empty), Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog progressDialog = new ProgressDialog(root.getContext());
            progressDialog.setTitle("Login ...");
            progressDialog.setMessage("Check password ...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Looper for Toast in a sub thread
                    Looper.prepare();

                    // simulate query
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // check
                    if (checkLogin(username, password)) {
                        redirectToSubPage(username);
                    } else {
                        Toast.makeText(activity, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                    Looper.loop();
                }
            }).start();
        }
    }


    protected boolean checkLogin(String username, String password) {
        // TODO: Is password in char[] safer?

        // TODO: SQLite
        if (Objects.equals(userDao.getPassword(username), password)) {
            return true;
        }
        return false;
    }

    protected void redirectToSubPage(String username) {
        Log.i(TAG, "redirectToSubPage: ");
        downloadRecourseForRedirecting();
    }

    protected void downloadRecourseForRedirecting() {
        ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setTitle("Login ...");
        progressDialog.setMessage("Download ...");
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Looper for Toast in a sub thread
                Looper.prepare();

                while (progressDialog.getProgress() < progressDialog.getMax()) {
                    progressDialog.setProgress(progressDialog.getProgress() + 1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();

                // finish downloading and redirect
                // TODO: et_username change?
                User user = null;
                try {
                    user = userDao.getUser(et_username.getText().toString());

                } catch (Exception e){

                } finally {
                    Log.i(TAG, "run: redirect user=" + user);
                    if(user != null){
                        // TODO: dangerous test Toast
                        Toast.makeText(activity,user.toString(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, MainActivity.class);
                        Bitmap bitmap = ((BitmapDrawable) iv_avatar.getDrawable()).getBitmap();
                        intent.putExtra(MainActivity.BUNDLE_AVATAR, bitmap);
                        intent.putExtra(MainActivity.BUNDLE_USER, user);
                        startActivity(intent);
                    }else {
                        Toast.makeText(activity,getString(R.string.login_select_error),Toast.LENGTH_SHORT).show();
                    }
                }

                Looper.loop();
            }
        }).start();
    }
}