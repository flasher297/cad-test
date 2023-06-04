package com.example.catsanddogs.sdk;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catsanddogs.sdk.log.interfaces.IClearable;
import com.example.catsanddogs.sdk.log.interfaces.ILogger;
import com.example.catsanddogs.sdk.log.Logger;
import com.example.catsanddogs.sdk.log.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class CsAnalytics {

    @NonNull
    private final Debouncer debouncer;
    @VisibleForTesting
    @NonNull List<ILogger> loggersList = new ArrayList<>();
    @VisibleForTesting
    final HashMap<CharSequence, TreeSet<Integer>> animalMap = new HashMap<>();
    private volatile boolean isLogCleared = false;

    public CsAnalytics(@NonNull Context context) {
        this(context, new Debouncer("Background", Debouncer.DEFAULT_DELAY));
    }

    public CsAnalytics(@NonNull Context context, @NonNull Debouncer debouncer) {
        this.debouncer = debouncer;
        loggersList.add(LoggerFactory.getLogger(Logger.LOGCAT, context));
        loggersList.add(LoggerFactory.getLogger(Logger.FILE, context));
    }


    public void trigger(@NonNull RecyclerView.ViewHolder holder, int position) {
        CharSequence key = holder.itemView.getContentDescription();
        TreeSet<Integer> treeSet = animalMap.get(key);
        if (treeSet != null) {
            int suitableAnimals = treeSet.headSet(position, true).size();
            debouncer.postDebounceAction(() -> {
                for (ILogger iLogger : loggersList) {
                    iLogger.log(makeLogEntry(position, key, suitableAnimals));
                }
            });
        }
    }

    @VisibleForTesting
    public String makeLogEntry(int position, CharSequence key, int suitableAnimals) {
        String sb = "Position " +
                position +
                ". There are " +
                suitableAnimals +
                " " +
                key +
                "s";

       return sb;
    }

    public void track(@NonNull RecyclerView.ViewHolder holder, int position) {
        CharSequence key = holder.itemView.getContentDescription();
        @Nullable
        TreeSet<Integer> treeSet = animalMap.get(key);
        if (treeSet == null) {
            treeSet = new TreeSet<>();
        }
        treeSet.add(position);
        animalMap.put(key, treeSet);
    }

    public void clear() {
        if (!isLogCleared) {
            for (ILogger iLogger : loggersList) {
                if (iLogger instanceof IClearable) {
                    debouncer.postImmediateAction(() -> {
                        ((IClearable) iLogger).clear();
                        isLogCleared = true;
                    });
                }
            }
        }
    }

}
