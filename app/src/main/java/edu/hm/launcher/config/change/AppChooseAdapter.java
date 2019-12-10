package edu.hm.launcher.config.change;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.hm.launcher.R;

/**
 * Adapter to display a list of all installed user apps.
 */
public class AppChooseAdapter extends BaseAdapter {

    private final List<ApplicationInfo> installedApps;
    private final AppChooseCallback callback;

    private final PackageManager packageManager;

    private final LayoutInflater inflater;

    AppChooseAdapter(Activity invokeActivity, AppChooseCallback callback) {
        this.callback = callback;
        inflater = LayoutInflater.from(invokeActivity.getApplicationContext());

        packageManager = invokeActivity.getPackageManager();
        List<ApplicationInfo> allApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        installedApps = new ArrayList<>();
        for (ApplicationInfo app : allApps) {
            if((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                installedApps.add(app);
            }
        }
    }

    @Override
    public int getCount() {
        return installedApps.size();
    }

    @Override
    public Object getItem(int position) {
        return installedApps.get(0);
    }

    @Override
    public long getItemId(int position) {
        return installedApps.get(position).uid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_app_choose, null);

        TextView appName = view.findViewById(R.id.list_item_app_choose_name);
        ImageView appIcon = view.findViewById(R.id.list_item_app_choose_icon);
        Button select = view.findViewById(R.id.list_item_app_choose_button);

        final ApplicationInfo currentApp = installedApps.get(position);

        appName.setText(currentApp.loadLabel(packageManager));
        appIcon.setImageDrawable(currentApp.loadIcon(packageManager));

        select.setOnClickListener(buttonView -> callback.onAppChosen(currentApp));

        return view;
    }
}
