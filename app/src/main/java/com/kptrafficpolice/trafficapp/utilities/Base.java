package com.kptrafficpolice.trafficapp.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class Base {
    private static Context context;

    private Base() {
    }

    public static void initialize(@NonNull Context context) {
        Base.context = context;
    }

    public static Context getContext() {
        Class var0 = Base.class;
        synchronized(Base.class) {
            if (context == null) {
                throw new NullPointerException("Call Base.initialize(context) within your Application onCreate() method.");
            } else {
                return context.getApplicationContext();
            }
        }
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Resources.Theme getTheme() {
        return getContext().getTheme();
    }

    public static AssetManager getAssets() {
        return getContext().getAssets();
    }

    public static Configuration getConfiguration() {
        return getResources().getConfiguration();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }
}

