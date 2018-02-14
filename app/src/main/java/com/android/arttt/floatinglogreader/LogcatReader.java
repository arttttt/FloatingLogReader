package com.android.arttt.floatinglogreader;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogcatReader {

    private final static String TAG = "FloatingLogReader";

    static Process mSuProcess = null;

    public static void read(Callback callback) {
        mSuProcess = SU.getSuProcess();

        if (mSuProcess == null || callback == null)
            return;

        PrintStream stream = new PrintStream(new BufferedOutputStream(mSuProcess.getOutputStream()));
        List<String> args = new ArrayList<String>(Arrays.asList("logcat", "-v", "tag"));
        stream.println(TextUtils.join(" ", args));
        stream.flush();
        stream.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(mSuProcess.getInputStream()));

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                callback.call(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mSuProcess.destroy();
        }
    }

    public static void close() {
        if (mSuProcess != null){
            try {
                mSuProcess.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface Callback {
        void call(String line);
    }
}
