package dev.santieinaki.fagamosbarullo.app;

import android.app.Application;

import butterknife.ButterKnife;
import dev.santieinaki.fagamosbarullo.BuildConfig;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        initButterKnifeDebug();
    }

    private void initButterKnifeDebug() {

        ButterKnife.setDebug(BuildConfig.DEBUG);
    }
}
