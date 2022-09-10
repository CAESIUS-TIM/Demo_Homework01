package com.example.homework01.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework01.Dao.UserDao;
import com.example.homework01.Dao.UserSQLiteOpenHelper;
import com.example.homework01.R;
import com.example.homework01.entity.User;
import com.example.homework01.impl.InitiatedViewer;
import com.example.homework01.view.MyView;
import com.example.homework01.view.PieChartManagger;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PieActivity extends AppCompatActivity implements InitiatedViewer {
    private PieChart pcMain;
    private MyView myView;

    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        userDao = new UserDao(this);

        initViews();
    }

    @Override
    public void findViews() {
        pcMain = findViewById(R.id.piechart);
        myView = findViewById(R.id.myview_pie);
    }

    @Override
    public void setViews() {

        myView.setText(getString(R.string.gender_ratio), "统计页");

        int gender_none = userDao.getNumberOfNone();
        int gender_male = userDao.getNumberOfMale();
        int gender_female = userDao.getNumberOfFemale();

        List<PieEntry> yvals = new ArrayList<>();

        yvals.add(new PieEntry(gender_none, "None"));
        yvals.add(new PieEntry(gender_male, "Male"));
        yvals.add(new PieEntry(gender_female, "Female"));

        // 设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFCF63B4"));
        colors.add(Color.parseColor("#FFEDC532"));
        colors.add(Color.parseColor("#FF54C29A"));
        PieChartManagger pieChartManagger = new PieChartManagger(pcMain);
        pieChartManagger.showSolidPieChart(yvals, colors);
    }

    @Override
    public void initViews() {
        findViews();
        setViews();
    }
}