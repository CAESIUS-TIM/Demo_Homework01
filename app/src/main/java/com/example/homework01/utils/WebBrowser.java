package com.example.homework01.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.test.internal.util.LogUtil;

public class WebBrowser {
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
    }
}
