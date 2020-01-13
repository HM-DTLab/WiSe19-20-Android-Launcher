package edu.hm.launcher.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_APP_CHOOSE = 0;
    public static final int REQUEST_INITIAL_CONFIG_SETUP = 1;

    private ConfigurationManager configurationManager;

    public static List<AppInfo> apps;
    public static ArrayAdapter<AppInfo> adapter;
    PackageManager packageManager;
    GridView grdView;

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

        adapter = null;
        apps =null;
        getApps();
        loadGridView();
        addGridListeners();

        registerForContextMenu(grdView);
    }

    /**
     * ContextMenu pop up when lon press on an app
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("App nicht mehr hier anzeigen");
        getMenuInflater().inflate(R.menu.delete_menu,menu);
    }

    /**
     * Delete selected App
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.delete_app){
            Toast.makeText(this,"App gelöscht",Toast.LENGTH_SHORT).show();

            return true;
        } else
            return super.onContextItemSelected(item);
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

    /**
     * run the app when clicked
     */
    public void addGridListeners() {
        try {
            grdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = packageManager.getLaunchIntentForPackage(apps.get(i).name.toString());
                    MainActivity.this.startActivity(intent);
                }
            });
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage().toString() + " Grid", Toast.LENGTH_LONG).show();
            Log.e("Error Grid", ex.getMessage().toString() + " Grid");
        }

    }

    /**
     * load the GridView to display the apps on the homeScreen
     */
    private void loadGridView() {

        try {
            grdView = findViewById(R.id.grd_allApps);
            if (adapter == null) {
                adapter = new ArrayAdapter<AppInfo>(this, R.layout.app_on_home_screen, apps) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        ViewHolderItem viewHolder ;

                        if (convertView == null) {
                            convertView = getLayoutInflater().inflate(
                                    R.layout.app_on_home_screen, parent, false
                            );
                            viewHolder = new ViewHolderItem();
                            viewHolder.icon =  convertView.findViewById(R.id.img_icon);
                            viewHolder.name =  convertView.findViewById(R.id.txt_name);
                            viewHolder.label =  convertView.findViewById(R.id.txt_label);

                            convertView.setTag(viewHolder);
                        } else {
                            viewHolder = (ViewHolderItem) convertView.getTag();
                        }

                        AppInfo appInfo = apps.get(position);
                        if (appInfo != null) {
                            viewHolder.icon.setImageDrawable(appInfo.icon);
                            viewHolder.label.setText(appInfo.label);
                            viewHolder.name.setText(appInfo.name);
                        }


                        return convertView;

                    }

                    final class ViewHolderItem {
                        ImageView icon;
                        TextView label;
                        TextView name;
                    }
                };
            }

            grdView.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage().toString() + " loadListView", Toast.LENGTH_LONG).show();
            Log.e("Error loadListView", ex.getMessage().toString() + " loadListView");
        }

    }


    /**
     * get all instaled apps in a list of AppInfo
     */
    private void getApps() {
        try {

            packageManager = getPackageManager();
            if (apps == null) {
                apps = new ArrayList<AppInfo>();

                Intent i = new Intent(Intent.ACTION_MAIN, null);
                i.addCategory(Intent.CATEGORY_LAUNCHER);

                List<ResolveInfo> availableApps = packageManager.queryIntentActivities(i, 0);
                for (ResolveInfo ri : availableApps) {
                    AppInfo appinfo = new AppInfo();
                    appinfo.label = ri.loadLabel(packageManager);
                    appinfo.name = ri.activityInfo.packageName;
                    appinfo.icon = ri.activityInfo.loadIcon(packageManager);
                    apps.add(appinfo);

                }
            }

        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage().toString() + " loadApps", Toast.LENGTH_LONG).show();
            Log.e("Error loadApps", ex.getMessage().toString() + " loadApps");
        }

    }



}
