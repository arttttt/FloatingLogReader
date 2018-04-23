package com.android.arttt.floatinglogreader;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    private final static String TAG = "FloatingLogReader";

    private static Pattern mLogPattern = Pattern.compile("(\\w)/([^:]+\\s*): (.*)");

    private static int getMessageLevel(char message) {
        int level = -1;
        switch (message) {
            case 'V': {
                level = Log.VERBOSE;
                break;
            }
            case 'D': {
                level = Log.DEBUG;
                break;
            }
            case 'I': {
                level = Log.INFO;
                break;
            }
            case 'W': {
                level = Log.WARN;
                break;
            }
            case 'E': {
                level = Log.ERROR;
                break;
            }
            case 'F': {
                level = 100;
                break;
            }
        }

        return level;
    }

    public static LogItem parseMessage(String message) {
        String outMessage;
        int level;

        Matcher matcher = mLogPattern.matcher(message);
        String owner;

        if (matcher.find()) {
            level = getMessageLevel(matcher.group(1).charAt(0));
            outMessage = matcher.group(3);
            owner = matcher.group(2);
        } else {
            level = -1;
            outMessage = message;
            owner = "";
        }

        return new LogItem(level, owner, outMessage);
    }
}
