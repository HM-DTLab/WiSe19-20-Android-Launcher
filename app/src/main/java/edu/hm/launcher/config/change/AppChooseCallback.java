package edu.hm.launcher.config.change;

import edu.hm.launcher.config.container.AppContainer;

@FunctionalInterface
public interface AppChooseCallback {

    /**
     * When an app was chosen.
     * @param applicationInfo The chosen App.
     */
    void onAppChosen(AppContainer applicationInfo);

}
