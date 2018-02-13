package com.android.arttt.floatinglogreader;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class LogService extends Service {

    private WindowManager.LayoutParams mWindowsLayoutParams;
    View root;
    LogAdapter mAdapter;
    LogReaderAsyncTask mLogReaderAsyncTask;

    private void updateLog(String log) {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mAdapter.addItem(new LogItem(log));
        windowManager.updateViewLayout(root, mWindowsLayoutParams);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        mWindowsLayoutParams = new WindowManager.LayoutParams(1536, 600,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        root = LayoutInflater.from(this).inflate(R.layout.floating_window, null);
        root.setOnTouchListener(new VerySimpleGestureDetector(new VerySimpleGestureDetector.OnVerySimpleGestureListener() {
            float oldX = 0;
            float oldY = 0;
            int x = 0;
            int y = 0;

            @Override
            public void onPress(MotionEvent event) {
                x = mWindowsLayoutParams.x;
                y = mWindowsLayoutParams.y;
                oldX = event.getRawX();
                oldY = event.getRawY();
            }

            @Override
            public void onDoublePress() {
                stopSelf();
            }

            @Override
            public void onMove(MotionEvent event) {
                mWindowsLayoutParams.x = (int) (x + (event.getRawX() - oldX));
                mWindowsLayoutParams.y = (int) (y + (event.getRawY() - oldY));
                windowManager.updateViewLayout(root, mWindowsLayoutParams);
            }
        }));

        RecyclerView recyclerView = root.findViewById(R.id.log);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LogAdapter();
        recyclerView.setAdapter(mAdapter);

        windowManager.addView(root, mWindowsLayoutParams);
        mLogReaderAsyncTask = new LogReaderAsyncTask();
        mLogReaderAsyncTask.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mLogReaderAsyncTask.cancel(true);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeViewImmediate(root);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class LogReaderAsyncTask extends AsyncTask<Void,String,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            LogcatReader.read(new LogcatReader.Callback() {
                @Override
                public void call(String line) {
                    publishProgress(line);
                }
            });

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            updateLog(values[0]);
        }

    }
}
