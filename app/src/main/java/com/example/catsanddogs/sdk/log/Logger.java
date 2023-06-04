package com.example.catsanddogs.sdk.log;

/**
 *  Types of loggers
 *  {@link #UI} - Used Toast to show log entry for user in UI
 *  {@link #LOGCAT} - Used logcat for logging
 *  {@link #FILE} - Used to write log to file in private directory
 */
public enum Logger {
    UI,
    LOGCAT,
    FILE
}