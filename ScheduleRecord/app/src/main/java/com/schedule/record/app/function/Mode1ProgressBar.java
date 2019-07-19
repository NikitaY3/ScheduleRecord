package com.schedule.record.app.function;

import android.widget.ProgressBar;

public class Mode1ProgressBar {

    private int progress,max;
    private ProgressBar mode1ProgressBar;

    public Mode1ProgressBar(int progress,int max,ProgressBar mode1ProgressBar) {
        this.progress = progress;
        this.max = max;
        this.mode1ProgressBar = mode1ProgressBar;
        ProgressSet();
    }
    private void ProgressSet(){
        mode1ProgressBar.setProgress(progress);
        mode1ProgressBar.setMax(max);
    }
}
