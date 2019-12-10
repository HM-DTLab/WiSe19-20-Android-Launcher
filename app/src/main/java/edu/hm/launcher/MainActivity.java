package edu.hm.launcher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
}
