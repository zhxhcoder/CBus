/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.creditease.livebuslib.liveevent;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

/**
 */
public class MainThreadManager {

    private static class SingletonHolder {
        private static final MainThreadManager INSTANCE = new MainThreadManager();
    }

    public static MainThreadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final Object mLock = new Object();

    private MainThreadManager() {
    }

    @Nullable
    private volatile Handler mMainHandler;

    public void postToMainThread(Runnable runnable) {
        if (mMainHandler == null) {
            synchronized (mLock) {
                if (mMainHandler == null) {
                    mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        //noinspection ConstantConditions
        mMainHandler.post(runnable);
    }

    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
