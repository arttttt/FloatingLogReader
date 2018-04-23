package com.android.arttt.floatinglogreader;

import java.io.BufferedOutputStream;
import java.io.PrintStream;

public class SU {
    public static Process getSuProcess() {
        try {
            return Runtime.getRuntime().exec("su");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
