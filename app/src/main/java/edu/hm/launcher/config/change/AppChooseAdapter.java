package edu.hm.launcher.config.change;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.hm.launcher.R;
import edu.hm.launcher.config.container.AppContainer;

/**
 * Adapter to display a list of all installed user apps.
 */
public class AppChooseAdapter extends RecyclerView.Adapter<AppChooseAdapter.AppViewHolder> {

    private final List<AppContainer> installedApps;
    private final AppChooseCallback callback;

    private final PackageManager packageManager;

    private final LayoutInflater inflater;

    public static class AppViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        ImageView appIcon;
        Button selectButton;

        public AppViewHolder(View view) {
            super(view);
            appName = view.findViewById(R.id.list_item_app_choose_name);
            appIcon = view.findViewById(R.id.list_item_app_choose_icon);
            selectButton = view.findViewById(R.id.list_item_app_choose_button);
        }

    }

    AppChooseAdapter(Activity invokeActivity, AppChooseCallback callback) {
        this.callback = callback;
        inflater = LayoutInflater.from(invokeActivity.getApplicationContext());

        packageManager = invokeActivity.getPackageManager();
        List<ApplicationInfo> allApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        installedApps = new ArrayList<>();
        for (ApplicationInfo app : allApps) {
            if((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                AppContainer container = new AppContainer(app.packageName);
                try {
                    container.setPackageManager(packageManager);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(invokeActivity.getApplicationContext(),
                            e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                installedApps.add(container);
            }
        }
    }

    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_app_choose, viewGroup, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        final AppContainer currentApp = installedApps.get(position);

        holder.appName.setText(currentApp.getLabel());
        holder.appIcon.setImageDrawable(currentApp.getIcon());

        holder.selectButton.setOnClickListener(buttonView -> callback.onAppChosen(currentApp));
    }

    @Override
    public int getItemCount() {
        return installedApps.size();
    }
}
