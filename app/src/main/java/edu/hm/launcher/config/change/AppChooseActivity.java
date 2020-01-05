package edu.hm.launcher.config.change;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import edu.hm.launcher.R;

/**
 * A Result based Activity that returns a chosen Apps PackageName.
 */
public class AppChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_choose);

        ListView apps = findViewById(R.id.choose_app_list_view);
        apps.setAdapter(new AppChooseAdapter(this, applicationInfo -> {
            Intent intent = new Intent();
            intent.putExtra("app_package_name", applicationInfo.packageName);
            setResult(RESULT_OK, intent);
            finish();
        }));
    }
}
