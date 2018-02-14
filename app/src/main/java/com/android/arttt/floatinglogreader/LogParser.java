package com.android.arttt.floatinglogreader;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    private final static String TAG = "FloatingLogReader";
//(\w)/([^(]+)\(\s*(\\d+)): (.*)
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
        }

        return level;
    }

    private static String getMessageOwner(String message) {
        int start = message.indexOf(':');
        String owner = "";
        if (start != -1)
            owner = message.substring(0, start).trim();

        return "";
    }

    public static LogItem parseMessage(String message) {
        String outMessage = "";
        int level = -1;

        Matcher matcher = mLogPattern.matcher(message);
        String owner = "";

        if (matcher.find()) {
            outMessage = matcher.group(3);
            owner = matcher.group(2);
            level = getMessageLevel(matcher.group(1).charAt(0));
        } else {
            outMessage = message;
        }

        return new LogItem(level, owner, outMessage);
    }
}
