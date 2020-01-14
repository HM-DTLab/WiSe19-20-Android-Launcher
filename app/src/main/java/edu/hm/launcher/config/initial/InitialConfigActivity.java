package edu.hm.launcher.config.initial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import edu.hm.launcher.R;
import edu.hm.launcher.config.container.ConfigurationContainer;

public class InitialConfigActivity extends AppCompatActivity {

    public static ConfigurationContainer initialContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_config);
    }

    public void onManualConfigurationButtonClick(View view) {
        initialContainer = new ConfigurationContainer();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void notImplementedButtonClick(View view) {
        Toast.makeText(getApplicationContext(),
                "Dieser Button hat noch keine Funktion",
                Toast.LENGTH_SHORT).show();
    }

}
