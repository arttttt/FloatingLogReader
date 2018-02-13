package com.android.arttt.floatinglogreader;


import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


class VerySimpleGestureDetector implements View.OnTouchListener {

    private VerySimpleGestureDetector.OnVerySimpleGestureListener mVerySimpleGestureListener;
    private long mLastTouchTime = 0;
    public VerySimpleGestureDetector(VerySimpleGestureDetector.OnVerySimpleGestureListener onVerySimpleGestureListener) {
        mVerySimpleGestureListener = onVerySimpleGestureListener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean result = false;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                result = true;
                if (event.getEventTime() - mLastTouchTime < ViewConfiguration.getDoubleTapTimeout())
                    mVerySimpleGestureListener.onDoublePress();
                mLastTouchTime = event.getEventTime();
                mVerySimpleGestureListener.onPress(event);
                break;
            }
            case MotionEvent.ACTION_UP: {
                result = true;
                mLastTouchTime = event.getEventTime();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                result = true;
                mVerySimpleGestureListener.onMove(event);
                break;
            }
        }

        return result;
    }

    interface OnVerySimpleGestureListener {
        void onPress(MotionEvent event);
        void onDoublePress();
        void onMove(MotionEvent event);
    }
}
