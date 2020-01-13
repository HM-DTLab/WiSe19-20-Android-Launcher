package edu.hm.launcher.config.change;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import edu.hm.launcher.R;

/**
 * A Result based Activity that returns a chosen Apps PackageName.
 */
public class AppChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_choose);

        RecyclerView recyclerView = findViewById(R.id.app_choose_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new AppChooseAdapter(this, applicationInfo -> {
            Intent intent = new Intent();
            intent.putExtra("app_package_name", applicationInfo.getPackageName());
            setResult(RESULT_OK, intent);
            finish();
        }));
    }
}
