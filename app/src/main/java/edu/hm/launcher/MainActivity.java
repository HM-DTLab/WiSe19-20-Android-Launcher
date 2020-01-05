package edu.hm.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;
import java.util.Observable;

import edu.hm.launcher.config.ConfigurationManager;
import edu.hm.launcher.config.change.AppChooseActivity;
import edu.hm.launcher.config.container.AppContainer;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_APP_CHOOSE = 0;

    private final ConfigurationManager configurationManager = new ConfigurationManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurationManager.addObserver(this::onConfigChanged);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_APP_CHOOSE) {
            if(resultCode == AppChooseActivity.RESULT_OK) {
                String appPackageName = data.getStringExtra("app_package_name");
                configurationManager.add(new AppContainer(appPackageName));
            }
        }
    }

    /**
     * opens a new window where all the apps are listed when the plus button is clicked
     * @param view
     */
    public void onPlusButtonClick(View view){
        Intent intent = new Intent(this, AppChooseActivity.class);
        startActivityForResult(intent, REQUEST_APP_CHOOSE);
    }

    /**
     * opens the chrome app when the chrome image is clicked
     * @param v
     */
    public void onChromeButtonClick(View v) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
        startActivity(launchIntent);
    }

    /**
     * This method will be invoked when the configuration changes.
     * @param ob The changed object (ignored).
     * @param arg Arguments from the changed object (ignored).
     */
    private void onConfigChanged(Observable ob, Object arg) {
        final List<AppContainer> toShowApps = configurationManager.getApps();

        // Write there code to show the apps in toShowApps List!
    }


//    this function gives the icon of an app back
//    public static Drawable getActivityIcon(Context context, String packageName, String activityName) {
//        PackageManager pm = context.getPackageManager();
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName(packageName, activityName));
//        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
//
//        return resolveInfo.loadIcon(pm);
//    }
}
