package edu.hm.launcher.config.change;

import android.content.pm.ApplicationInfo;

@FunctionalInterface
public interface AppChooseCallback {

    /**
     * When an app was chosen.
     * @param applicationInfo The chosen App.
     */
    void onAppChosen(ApplicationInfo applicationInfo);

}
