package com.example.catsanddogs.sdk.log.loggers;

import android.content.Context;

import com.example.catsanddogs.sdk.FileRepository;
import com.example.catsanddogs.sdk.log.Logger;
import com.example.catsanddogs.sdk.log.LoggerFactory;
import com.example.catsanddogs.sdk.log.interfaces.IClearable;
import com.example.catsanddogs.sdk.log.interfaces.ILogger;

public class PersistentLogger implements ILogger, IClearable {

    public static final String TAG = PersistentLogger.class.getSimpleName();
    private final FileRepository fileRepository;
    private final ILogger decoratedLogger;

    public PersistentLogger(Context context, FileRepository fileRepository, Logger additionalLogger) {
        this.fileRepository = fileRepository;
        this.decoratedLogger = LoggerFactory.getLogger(additionalLogger, context);
    }

    @Override
    public void log(String logEntry) {
       fileRepository.writeNewEntry(logEntry);
       String res = fileRepository.readLastEntry();
       decoratedLogger.log(res);
    }

    @Override
    public void clear() {
        fileRepository.clear();
    }
}
