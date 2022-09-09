package com.example.homework01.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.PathUtils;
import com.example.homework01.R;
import com.example.homework01.impl.OnSelectedListener;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;

public class SelectPicturePopupWindow implements View.OnClickListener {
    public static final int ID_CANCEL = R.id.popup_button_cancel;
    public static final int ID_CAMERA = R.id.popup_button_camera;
    public static final int ID_LOCAL = R.id.popup_button_local;

    private LinearLayout popup_ll;
    private Button popup_bt_camera;
    private Button popup_bt_local;
    private Button popup_bt_cancel;
    private View popupView;
    private PopupWindow popupWindow;
    private Activity activity;
    private OnSelectedListener mOnSelectedListener;

    public SelectPicturePopupWindow(Context context) {

        // TODO: ?
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popupwindow_selectimage, null);

        popup_bt_camera = popupView.findViewById(R.id.popup_button_camera);
        popup_bt_local = popupView.findViewById(R.id.popup_button_local);
        popup_bt_cancel = popupView.findViewById(R.id.popup_button_cancel);

        popup_bt_camera.setOnClickListener(this);
        popup_bt_local.setOnClickListener(this);
        popup_bt_cancel.setOnClickListener(this);
    }

    public void showPopupWindow(Activity activity) {
        this.activity = activity;

        popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.teal_200))); // TODO: ?
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(R.style.Animation_InputMethod); // TODO: set style.Animation_InputMethod
        popupWindow.setOutsideTouchable(false);

        popupWindow.update();
    }

    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            activity = null;
            popupWindow = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_button_camera:
                if(mOnSelectedListener != null){
                    mOnSelectedListener.onSelected(v,ID_CAMERA);
                }
                break;
            case R.id.popup_button_local:
                if(mOnSelectedListener != null){
                    mOnSelectedListener.onSelected(v,ID_LOCAL);
                }
                break;
            case R.id.popup_button_cancel:
                if(mOnSelectedListener != null){
                    mOnSelectedListener.onSelected(v,ID_CANCEL);
                }
                break;
        }
    }

    public void setOnSelectedListener(OnSelectedListener mOnSelectedListener){
        this.mOnSelectedListener = mOnSelectedListener;
    }
}
