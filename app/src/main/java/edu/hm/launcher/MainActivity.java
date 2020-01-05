package edu.hm.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
