package com.example.homework01.fragment;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.homework01.Dao.UserDao;
import com.example.homework01.R;
import com.example.homework01.entity.User;
import com.example.homework01.impl.NeedAllFragment;
import com.example.homework01.utils.ImageHelper;

import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class RegisterFragment extends NeedAllFragment implements View.OnClickListener {

    private static final String TAG = RegisterFragment.class.getSimpleName();

    private View v_select;
    private ImageView iv_select;
    private EditText et_username;
    private EditText et_password;
    private EditText et_checkPassword;
    private TextView tv_birthday;
    private EditText et_address;
    private EditText et_email;
    private EditText et_job;
    private EditText et_company;
    private Button bt_register;
    private RadioGroup rg_gender;
    private SwitchCompat sw_birthday;

    // misc
    public static final int MSG_ET_BIRTHDAY = 100;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_ET_BIRTHDAY) {
                tv_birthday.setText((String) msg.obj);
            }
            return false;
        }
    });

    public RegisterFragment(AppCompatActivity activityContext, FragmentManager fragmentManager, UserDao userDao) {
        super(activityContext, fragmentManager, userDao);
    }

    public void setAvatar(Drawable drawable) {
        iv_select.setImageDrawable(drawable);
    }

    public void setAvatar(Bitmap bitmap) {
        iv_select.setImageBitmap(bitmap);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_register, container, false);
        }

        initViews();

        return root;
    }

    @Override
    public void findViews() {
        v_select = root.findViewById(R.id.register_fragment_picture);
        iv_select = v_select.findViewById(R.id.image_select);
        et_username = root.findViewById(R.id.register_editText_username);
        et_password = root.findViewById(R.id.register_editText_password);
        et_checkPassword = root.findViewById(R.id.register_editText_checkPassword);
        tv_birthday = root.findViewById(R.id.register_editText_birthday);
        et_address = root.findViewById(R.id.register_editText_address);
        et_email = root.findViewById(R.id.register_editText_email);
        et_job = root.findViewById(R.id.register_editText_job);
        et_company = root.findViewById(R.id.register_editText_company);
        bt_register = root.findViewById(R.id.register_button_register);
        rg_gender = root.findViewById(R.id.register_radioGroup_gender);
        sw_birthday = root.findViewById(R.id.register_switch_birthday);
    }

    @Override
    public void setViews() {
        bt_register.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
        tv_birthday.setEnabled(sw_birthday.isChecked());
        sw_birthday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tv_birthday.setEnabled(isChecked);
            if (!isChecked) {
                tv_birthday.setText(getString(R.string.birthday));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button_register:
                Log.i(TAG, "onClick: register");
                User user = getUser();
                if (isValidRegister(user)) {
                    if (registerUser(user, iv_select.getDrawable())) {
                        Log.i(TAG, "onClick: " + getString(R.string.register_register_success) + " user=" + user);
                        Toast.makeText(activity, getString(R.string.register_register_success), Toast.LENGTH_SHORT).show();
                        replaceFragment();
                    }
                }
                break;
            case R.id.register_editText_birthday:
                Log.i(TAG, "onClick: birthday");
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(activity,
                        (view, year, month, dayOfMonth) -> {
                            // Thread, maybe have waited for a long time
                            Calendar now = Calendar.getInstance();
                            Calendar birthday = new GregorianCalendar(year, month, dayOfMonth);
                            if (now.after(birthday)) {
                                Message message = new Message();
                                message.what = MSG_ET_BIRTHDAY;
                                message.obj = String.format("%d %d %d", year, month, dayOfMonth);
                                handler.sendMessage(message);
                            } else {
                                Toast.makeText(activity, getString(R.string.register_birthday_future), Toast.LENGTH_SHORT).show();
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
                break;
        }
    }

    private User getUser() {
        User user = new User();
        user.setUsername(et_username.getText().toString());
        user.setPassword(et_password.getText().toString());
        user.setAvatar(Paths.get(getString(R.string.avatar), user.getUsername() + ".png").toString());
        int radioButtonId = rg_gender.getCheckedRadioButtonId();
        switch (radioButtonId) {
            case R.id.register_radioButton_none:
                user.setGender(User.GENDER_NONE);
                break;
            case R.id.register_radioButton_female:
                user.setGender(User.GENDER_FEMALE);
                break;
            case R.id.register_radioButton_male:
                user.setGender(User.GENDER_MALE);
                break;
            default:
                throw new NullPointerException("radioGroup got outside ids.");
        }
        String birthdayText = tv_birthday.getText().toString();
        // TODO: birthday
        // TODO: regex (length)
        // TODO: email online check
        user.setEmail(et_email.getText().toString());
        user.setAddress(et_address.getText().toString());
        user.setJob(et_job.getText().toString());
        user.setCompany(et_company.getText().toString());
        return user;
    }

    private boolean registerUser(User user, Drawable avatar) {
        if (saveAvatar(user, avatar)) {
            if (userDao.insertUser(user)) {
                return true;
            } else {
                Toast.makeText(activity, getString(R.string.register_insert_error), Toast.LENGTH_SHORT).show();
                if (!deleteAvatar(user)) {
                    // TODO: cannot delete
                    Toast.makeText(activity, getString(R.string.register_deleteAvatar_error), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(activity, getString(R.string.register_saveAvatar_error), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean saveAvatar(User user, Drawable avatar) {
        return saveAvatar(user.getUsername(), avatar);
    }

    private boolean saveAvatar(String username, Drawable avatar) {
        return ImageHelper.saveImgToApp(root.getContext(), ImageHelper.imageScale(avatar, 180, 180), Bitmap.CompressFormat.PNG,
                Paths.get(getString(R.string.avatar), username + ".png").toString());
    }

    private boolean deleteAvatar(User user) {
        return deleteAvatar(user.getUsername());
    }

    private boolean deleteAvatar(String username) {
        return ImageHelper.deleteImgInApp(Paths.get(getString(R.string.avatar), username + ".png").toString());
    }

    private boolean isValidRegister(User user) {

        if (Objects.equals(user.getUsername(), "")) {
            Toast.makeText(activity, getString(R.string.register_username_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Objects.equals(user.getPassword(), "")) {
            Toast.makeText(activity, getString(R.string.register_password_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Objects.equals(user.getPassword(), et_checkPassword.getText().toString())) {
            Toast.makeText(activity, getString(R.string.register_password_diff), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userDao.isUserExisting(user.getUsername())) {
            Toast.makeText(activity, String.format(getString(R.string.register_user_exist), user.getUsername()), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void replaceFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment(activity, fragmentManager, userDao);
        Bundle bundle = new Bundle();
        Log.i(TAG, "onClick: ");
        bundle.putParcelable(LoginFragment.BUNDLE_AVATAR, ((BitmapDrawable) iv_select.getDrawable()).getBitmap());
        bundle.putString(LoginFragment.BUNDLE_USERNAME, et_username.getText().toString());
        bundle.putString(LoginFragment.BUNDLE_PASSWORD, et_password.getText().toString());
        loginFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_frameLayout, loginFragment);
        fragmentTransaction.commit();
    }
}
