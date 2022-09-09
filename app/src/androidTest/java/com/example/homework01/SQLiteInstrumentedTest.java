package com.example.homework01;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.homework01.Dao.UserDao;
import com.example.homework01.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SQLiteInstrumentedTest {
    public static final String TAG = SQLiteInstrumentedTest.class.getSimpleName();

    @Test
    public void createUser() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.homework01", appContext.getPackageName());

        UserDao userDao = new UserDao(appContext);
        User user = new User("timxing", "123456", "test.png");
        assertEquals(true, userDao.insertUser(user));
        user.setGender(1);
        user.setEmail("19001727@mail.ecust.edu.cn");
        assertEquals(true, userDao.updateUser(user));
        assertEquals(user.getPassword(), userDao.getPassword(user));
        Log.i(TAG, "createUser: " + userDao.getPassword(user));
    }
}

