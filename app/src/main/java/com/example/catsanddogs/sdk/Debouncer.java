package com.example.catsanddogs.sdk;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

public class Debouncer extends HandlerThread {

    public static final long DEFAULT_DELAY = 2000;
    private static final String TAG = Debouncer.class.getSimpleName();
    @VisibleForTesting
    final Handler workerHandler;
    private final long delayTime;

    private Runnable mActiveRunnable;
    public Debouncer(@NonNull final String name, long delayTime) {
        super(TAG + name );

        this.delayTime = delayTime;
        start();
        workerHandler = new Handler(getLooper());
    }

    public void postDebounceAction(final ActionCallback actionCallback) {
        deleteActiveRequest();
        mActiveRunnable = actionCallback::action;
        workerHandler.postDelayed(mActiveRunnable, delayTime);
    }

    public void postImmediateAction(final ActionCallback actionCallback) {
        mActiveRunnable = actionCallback::action;
        workerHandler.post(mActiveRunnable);
    }

    private void deleteActiveRequest() {
        if (mActiveRunnable != null) {
            workerHandler.removeCallbacks(mActiveRunnable);
        }
    }

    public interface ActionCallback {

        void action();

    }


}