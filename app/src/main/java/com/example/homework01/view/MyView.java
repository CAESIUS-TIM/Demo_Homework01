package com.example.homework01.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * 自定义控件
 */
public class MyView extends LinearLayout {
    private TextView textView2;
    private TextView textView1;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置背景
        setBackgroundColor(Color.parseColor("#ffacacac"));
        //设置居中
        setGravity(Gravity.CENTER);
        setPadding(10, 10, 10, 10);
        //设置垂直
        setOrientation(VERTICAL);
        textView1 = new TextView(getContext());
        textView1.setTextColor(Color.WHITE);
        textView1.setGravity(Gravity.CENTER);
        textView2 = new TextView(getContext());
        textView2.setTextColor(Color.WHITE);
        textView2.setGravity(Gravity.CENTER);
        addView(textView1);
        addView(textView2);
    }

    public void setText(String firstText, String secText) {
        //判断用户名是否为空，为空则不显示
        if (!TextUtils.isEmpty(firstText)) {
            textView2.setText("Welcome：" + firstText);
        }
        //当前页面一定不为空
        textView1.setText("当前页面：" + secText);
    }
}
