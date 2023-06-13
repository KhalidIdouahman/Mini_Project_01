package com.example.miniproject_01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyOnSwipeListener implements View.OnTouchListener {
    private static final int THRESHOLD = 100;
    private Context context;
    private GestureDetector myGestureDetector;

    public MyOnSwipeListener(Context context) {
        this.context = context;
        this.myGestureDetector = new GestureDetector(context , new MyGestureDetectorListener());
    }


    class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() >= THRESHOLD) {
                swipeLeft();
            } else if (e2.getX() - e1.getX() >= THRESHOLD){
                swipeRight();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    public void swipeLeft() {}
    public void swipeRight() {}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        myGestureDetector.onTouchEvent(event);
//        this line is too important because when i make the line above in the return statement it doesn't work
//        i think that onTouchEvent returns false ,but why i don't know .
        return true;
    }
}
