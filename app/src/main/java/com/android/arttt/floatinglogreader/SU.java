package com.android.arttt.floatinglogreader;

import java.io.BufferedOutputStream;
import java.io.PrintStream;

public class SU {
    public static void requestRoot() {
        try {
            Process process = Runtime.getRuntime().exec("su");

            PrintStream outputStream = new PrintStream(new BufferedOutputStream(process.getOutputStream()));
            outputStream.println("echo hello");
            outputStream.close();
            process.waitFor();
            process.destroy();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Process getSuProcess() {
        try {
            return Runtime.getRuntime().exec("su");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
