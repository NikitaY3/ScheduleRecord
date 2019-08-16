//package com.schedule.record.app;
//
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//
//import static android.content.ContentValues.TAG;
//
//public class MyGestureListener implements GestureDetector.OnGestureListener {
//
//    @Override
//    public boolean onDown(MotionEvent motionEvent) {
//        Log.d(TAG, "onDown:按下");
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent motionEvent) {
//        Log.d(TAG, "onShowPress:手指按下一段时间,不过还没到长按");
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent motionEvent) {
//        Log.d(TAG, "onSingleTapUp:手指离开屏幕的一瞬间");
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        Log.d(TAG, "onScroll:在触摸屏上滑动");
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent motionEvent) {
//        Log.d(TAG, "onLongPress:长按并且没有松开");
//    }
//
//    @Override
//    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        Log.d(TAG, "onFling:迅速滑动，并松开");
//        return false;
//    }
//}
