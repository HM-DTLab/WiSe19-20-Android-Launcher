package edu.hm.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Observable;

import edu.hm.launcher.config.ConfigurationManager;
import edu.hm.launcher.config.change.AppChooseActivity;
import edu.hm.launcher.config.container.AppContainer;
import edu.hm.launcher.config.initial.InitialConfigActivity;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_APP_CHOOSE = 0;
    public static final int REQUEST_INITIAL_CONFIG_SETUP = 1;

    private ConfigurationManager configurationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checks whether its the first time the app is started
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("initial", Context.MODE_PRIVATE);
        boolean alreadySetup = sharedPreferences.getBoolean("setup", false);

        if(alreadySetup) {
            // If not simply load and initialize the configuration
            configurationManager = new ConfigurationManager(this);
            configurationManager.addObserver(this::onConfigChanged);
            try {
                configurationManager.loadConfig();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),
                        "Could not load configuration: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(this, InitialConfigActivity.class);
            startActivityForResult(intent, REQUEST_INITIAL_CONFIG_SETUP);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_APP_CHOOSE) {
            if(resultCode == AppChooseActivity.RESULT_OK) {
                String appPackageName = data.getStringExtra("app_package_name");
                configurationManager.add(new AppContainer(appPackageName));
            }
        } else if(requestCode == REQUEST_INITIAL_CONFIG_SETUP) {
            if(resultCode == InitialConfigActivity.RESULT_OK) {
                configurationManager = new ConfigurationManager(this, InitialConfigActivity.initialContainer);
                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("initial", Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("setup", true).apply();
            } else {
                configurationManager = new ConfigurationManager(this);
            }
            configurationManager.addObserver(this::onConfigChanged);
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

        // You can remove this afterwards
        Toast.makeText(getApplicationContext(),"Apps changed, now " + toShowApps.size(), Toast.LENGTH_SHORT).show();
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
