package com.schedule.record.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;

public class Mode3CompletionRatio extends AppCompatActivity {
    LinkedList<Double> yList;
    LinkedList<String> xRawData;
    ChartView chartView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode3_completion_ratio);
        chartView = (ChartView) findViewById(R.id.chartView);

        yList = new LinkedList<>();
        yList.add(2.203);
        yList.add(4.05);
        yList.add(6.60);
        yList.add(3.08);
        yList.add(4.32);
        yList.add(2.0);
        yList.add(5.0);

        xRawData = new LinkedList<>();
        xRawData.add("05-19");
        xRawData.add("05-20");
        xRawData.add("05-21");
        xRawData.add("05-22");
        xRawData.add("05-23");
        xRawData.add("05-24");
        xRawData.add("05-25");

        chartView.setData(yList , xRawData , 8 , 2);
    }
}
