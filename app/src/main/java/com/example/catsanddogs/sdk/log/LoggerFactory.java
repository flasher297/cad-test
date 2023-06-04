package com.example.catsanddogs.sdk.log;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.catsanddogs.sdk.FileRepository;
import com.example.catsanddogs.sdk.log.interfaces.ILogger;
import com.example.catsanddogs.sdk.log.loggers.LogcatLogger;
import com.example.catsanddogs.sdk.log.loggers.PersistentLogger;
import com.example.catsanddogs.sdk.log.loggers.ToastLogger;


public class LoggerFactory {
    public static ILogger getLogger(@NonNull final Logger logger, @NonNull final  Context context) {

        switch (logger) {
            case UI:
                return new ToastLogger(context);
            case LOGCAT:
                return new LogcatLogger();
            case FILE:
                return new PersistentLogger(context, new FileRepository(context, FileRepository.DEFAULT_FILE_NAME), Logger.UI);
        }

        return null;
    }
}