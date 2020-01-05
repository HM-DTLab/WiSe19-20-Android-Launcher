package edu.hm.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.hm.launcher.config.change.AppChooseActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_APP_CHOOSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launchAppChooseActivity();
    }

    /**
     * Launches the App choose Activity with RequestCode REQUEST_APP_CHOOSE.
     */
    private void launchAppChooseActivity() {
        Intent intent = new Intent(this, AppChooseActivity.class);
        startActivityForResult(intent, REQUEST_APP_CHOOSE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_APP_CHOOSE) {
            if(resultCode == AppChooseActivity.RESULT_OK) {
                String appPackageName = data.getStringExtra("app_package_name");
                try {
                    getPackageManager().getApplicationInfo(appPackageName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * opens a new window where all the apps are listed when the plus button is clicked
     * @param view
     */
    public void onPlusBottonClick(View view){
        Intent intent = new Intent(this, AppsDrawer.class);
        startActivity(intent);
    }

    /**
     * opens the chrome app when the chrome image is clicked
     * @param v
     */
    public void onChromeButtonClick(View v) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
        startActivity(launchIntent);
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
