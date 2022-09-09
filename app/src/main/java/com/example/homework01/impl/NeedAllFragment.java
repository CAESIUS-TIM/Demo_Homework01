package com.example.homework01.impl;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.homework01.Dao.UserDao;

public abstract class NeedAllFragment extends Fragment implements InitiatedViewer{
    protected View root;
    protected final AppCompatActivity activity;
    protected final FragmentManager fragmentManager;
    protected final UserDao userDao;

    public NeedAllFragment(AppCompatActivity activity, FragmentManager fragmentManager, UserDao userDao) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.userDao = userDao;
    }

    protected void initView(){
       findViews();
       setViews();
    }
}
