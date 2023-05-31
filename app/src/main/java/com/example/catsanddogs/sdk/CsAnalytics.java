package com.example.catsanddogs.sdk;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CsAnalytics {

    public CsAnalytics(@NonNull Context context) {
    }

    public void trigger(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Displays the pop-up (Toast) with the dog or cat count.
        //Don't forget the thread and debounce timer.
    }

    public void track(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Registers all the pets from each grid.
    }

    public void clear() {
        //Clears the log file.
    }

}
