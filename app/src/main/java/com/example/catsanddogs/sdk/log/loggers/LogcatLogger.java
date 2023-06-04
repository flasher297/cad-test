package com.example.catsanddogs.sdk.log.loggers;

import android.util.Log;

import com.example.catsanddogs.sdk.log.interfaces.ILogger;

public class LogcatLogger implements ILogger {

    public static final String TAG = LogcatLogger.class.getSimpleName();

    @Override
    public void log(String logEntry) {
        Log.d(TAG, logEntry);
    }

}
