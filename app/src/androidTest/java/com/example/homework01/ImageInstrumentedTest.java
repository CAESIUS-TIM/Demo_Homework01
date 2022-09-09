package com.example.homework01;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.homework01.utils.ImageHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.file.Paths;

@RunWith(AndroidJUnit4.class)
public class ImageInstrumentedTest {
    public static final String TAG = ImageInstrumentedTest.class.getSimpleName();

    @Test
    public void download() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.homework01", appContext.getPackageName());

        ImageHelper.saveImgToLocal(appContext,
                "https://avatars.githubusercontent.com/u/9884053?s=48&v=4",
                Paths.get(appContext.getString(R.string.app_name), "test.png").toString());
    }

    @Test
    public void load() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.homework01", appContext.getPackageName());

        assertEquals(true,
                ImageHelper.saveImgToApp(appContext,
                        appContext.getDrawable(R.drawable.avatar01),
                        Bitmap.CompressFormat.PNG,
                        Paths.get(appContext.getString(R.string.avatar), "root.png").toString())
        );
    }

    @Test
    public void packageName() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.homework01", appContext.getPackageName());
        PackageManager pm = appContext.getPackageManager();
        String appName = appContext.getApplicationInfo().loadLabel(pm).toString();
        Log.i(TAG, "packageName: " + appName);
    }
}
