package edu.hm.launcher.config.container;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * Simple container to hold information about an App
 */
public class AppContainer {

    private final String packageName;

    private Drawable drawable = null;
    private String label = null;

    public AppContainer(String appName) {
        this.packageName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageManager(PackageManager packageManager) throws PackageManager.NameNotFoundException {
        final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName,
                PackageManager.GET_META_DATA);
        drawable = applicationInfo.loadIcon(packageManager);
        label = applicationInfo.loadLabel(packageManager).toString();
    }

    public Drawable getIcon() {
        return drawable;
    }

    public String getLabel() {
        return label;
    }
}
