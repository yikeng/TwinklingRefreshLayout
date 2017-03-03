package com.lcodecore.tkrefreshlayout;

import android.view.MotionEvent;

/**
 * Created by lcodecore on 2017/3/1.
 */

public interface IDecorator {
    boolean interceptTouchEvent(MotionEvent ev);

    boolean dealTouchEvent(MotionEvent ev);

    boolean onFingerScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY, float velocityY);

    boolean onFingerFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
