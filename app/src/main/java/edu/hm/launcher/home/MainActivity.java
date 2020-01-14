package edu.hm.launcher.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import edu.hm.launcher.R;
import edu.hm.launcher.config.ConfigurationManager;
import edu.hm.launcher.config.change.AppChooseActivity;
import edu.hm.launcher.config.container.AppContainer;
import edu.hm.launcher.config.initial.InitialConfigActivity;
import edu.hm.launcher.tutorial.FirstTutorialPage;

public class MainActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

    public static final int REQUEST_APP_CHOOSE = 0;
    public static final int REQUEST_INITIAL_CONFIG_SETUP = 1;

    private ConfigurationManager configurationManager;

    public static ArrayAdapter<AppContainer> adapter;
    private PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();

        loadGridView();

        // Checks whether its the first time the app is started
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("initial", Context.MODE_PRIVATE);
        boolean alreadySetup = sharedPreferences.getBoolean("setup", false);

        if (alreadySetup) {
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
        if (requestCode == REQUEST_APP_CHOOSE) {
            if (resultCode == AppChooseActivity.RESULT_OK) {
                String appPackageName = data.getStringExtra("app_package_name");
                if(!configurationManager.add(new AppContainer(appPackageName))) {

                }
            }
        } else if (requestCode == REQUEST_INITIAL_CONFIG_SETUP) {
            if (resultCode == InitialConfigActivity.RESULT_OK) {
                configurationManager = new ConfigurationManager(this, InitialConfigActivity.initialContainer);
                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("initial", Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("setup", true).apply();
            } else {
                configurationManager = new ConfigurationManager(this);
            }
            configurationManager.addObserver(this::onConfigChanged);
            onConfigChanged(null, null);
        }
    }

    public boolean onTouchEvent(View view, MotionEvent touchEvent) {
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1+300 < x2){
                    Intent intent = new Intent(this, FirstTutorialPage.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
                }
        }
        return true;
    }

    public void openAppChooseActivity() {
        Intent intent = new Intent(this, AppChooseActivity.class);
        startActivityForResult(intent, REQUEST_APP_CHOOSE);
    }


    /**
     * This method will be invoked when the configuration changes.
     *
     * @param ob  The changed object (ignored).
     * @param arg Arguments from the changed object (ignored).
     */
    private void onConfigChanged(Observable ob, Object arg) {
        List<AppContainer> apps = configurationManager.getApps();
        apps.add(new AppContainer());
        adapter.clear();
        adapter.addAll(apps);
        adapter.notifyDataSetChanged();
    }

    /**
     * load the GridView to display the apps on the homeScreen
     */
    private void loadGridView() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        final int width = metrics.widthPixels;
        ConstraintLayout.LayoutParams layoutSize = new ConstraintLayout.LayoutParams(width / 2, width / 2);
        try {
            GridView grdView = findViewById(R.id.grd_allApps);
            grdView.setOnTouchListener(this::onTouchEvent);
            if (adapter == null) {
                adapter = new ArrayAdapter<AppContainer>(this, R.layout.app_on_home_screen, new ArrayList<>()) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        AppContainer currentApp = getItem(position);

                        ViewHolderItem viewHolder;

                        if (convertView == null) {
                            convertView = getLayoutInflater().inflate(
                                    R.layout.app_on_home_screen, parent, false
                            );
                            viewHolder = new ViewHolderItem();
                            convertView.setLayoutParams(layoutSize);
                        } else {
                            viewHolder = (ViewHolderItem) convertView.getTag();
                        }

                        viewHolder.icon = convertView.findViewById(R.id.img_icon);
                        viewHolder.label = convertView.findViewById(R.id.txt_label);

                        if(!currentApp.isAddNewApp) {
                            convertView.setOnLongClickListener(v -> {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("App entfernen")
                                        .setMessage("Möchten Sie die App von dem Hauptbildschirm entfernen?")
                                        .setPositiveButton("Ja", (dialog, id) ->
                                                configurationManager.removeAt(position))
                                        .setNegativeButton("Nein", (dialog, id) -> {
                                        })
                                        .create().show();
                                return true;
                            });

                            convertView.setOnClickListener(v -> {
                                Intent intent = packageManager.getLaunchIntentForPackage(currentApp.getPackageName());
                                MainActivity.this.startActivity(intent);
                            });
                        } else {
                            convertView.setOnClickListener(v -> openAppChooseActivity());
                            convertView.setOnLongClickListener(null);
                        }

                        convertView.setTag(viewHolder);

                        if(!currentApp.isAddNewApp) {
                            viewHolder.icon.setImageDrawable(currentApp.getIcon());
                            viewHolder.label.setText(currentApp.getLabel());
                        } else {
                            viewHolder.icon.setImageDrawable(getResources().
                                    getDrawable(getResources().getIdentifier("@drawable/plus_button",
                                            null, getPackageName())));
                            viewHolder.label.setText("App hinzufügen");
                        }

                        return convertView;
                    }

                    final class ViewHolderItem {
                        ImageView icon;
                        TextView label;
                    }
                };
            }

            grdView.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage() + " loadListView", Toast.LENGTH_LONG).show();
            Log.e("Error loadListView", ex.getMessage() + " loadListView");
        }
    }
}
