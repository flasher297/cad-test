package com.example.catsanddogs.sdk.log.loggers;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.catsanddogs.sdk.log.interfaces.ILogger;

public class ToastLogger implements ILogger {

    public static final String TAG = ToastLogger.class.getSimpleName();
    private final Context context;

    public ToastLogger(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public void log(String logEntry) {
        Toast.makeText(context, logEntry, Toast.LENGTH_SHORT).show();
    }

}
