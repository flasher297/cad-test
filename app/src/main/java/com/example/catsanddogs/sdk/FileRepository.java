package com.example.catsanddogs.sdk;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileRepository {

    public static final String DEFAULT_FILE_NAME = "logs.dat";
    private static final String TAG = FileRepository.class.getSimpleName();

    @NonNull
    private final Context context;
    @NonNull
    private final String fileName;

    public FileRepository(@NonNull Context context, @NonNull String fileName) {
        this.context = context;
        this.fileName = fileName;
    }
    public void clear()  {
        File file = context.getFileStreamPath(fileName);
        Log.d(TAG, "clear File");
        if (file.exists()) {
            file.delete();
            Log.d(TAG, "DELETE File");
        } else {
            Log.d(TAG, "No need to DELETE file");

        }
    }
    @WorkerThread
    public String readLastEntry()  {
        FileInputStream fis = null;
        BufferedReader input = null;

        try {
            Log.d(TAG, "readLastEntry File");
            File file = context.getFileStreamPath(fileName);
            if (!file.exists()) {
                return "";
            } else {
                fis = context.openFileInput(this.fileName);
                input = new BufferedReader(new InputStreamReader(fis));
                // Builder is here just for additional logging.
                // Could be disabled later with BuildConfig.DEBUG
                StringBuilder builder = new StringBuilder();
                String line;
                String lastLine = "";
                while ((line = input.readLine()) != null) {
                    lastLine = line;
                    builder.append(line);
                }
                Log.d(TAG, "File all: " + builder);
                Log.d(TAG, "File last line: " + lastLine);

                return lastLine;
            }
        } catch (Exception e) {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception closingException) {
                Log.e(TAG, closingException.getMessage());
            }
            return "";
        }
    }
    @WorkerThread
    public void writeNewEntry(String logEntry)  {
        FileOutputStream fos = null;
        OutputStreamWriter outStreamWriter = null;
        try {
            File file = context.getFileStreamPath(fileName);
            if (!file.exists()) {
                Log.d(TAG, "Create new file");
                file.createNewFile();
            } else {
                Log.d(TAG, "File Already exists");
            }

            //Append
            fos = new FileOutputStream(file, true);
            outStreamWriter = new OutputStreamWriter(fos);
            outStreamWriter.append(logEntry).append("\n");
            outStreamWriter.flush();
            Log.d(TAG, "Write appendix");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                if (outStreamWriter != null) {
                    outStreamWriter.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception closingException) {
                Log.e(FileRepository.class.getSimpleName(), closingException.toString());
            }
        }
    }

}
