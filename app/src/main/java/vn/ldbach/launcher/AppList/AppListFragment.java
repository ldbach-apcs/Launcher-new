package vn.ldbach.launcher.AppList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.ldbach.launcher.LauncherFragment;
import vn.ldbach.launcher.R;

/**
 * Created by Duy-Bach on 3/18/2018.
 */

public class AppListFragment extends LauncherFragment {

    RecyclerView appView = null;
    List<AppDetail> appList = null;
    AppAdapter appAdapter;
    TextView appCount = null;
    private BroadcastReceiver appInstallReceiver, appRemoveReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_list, container, false);
        appView = rootView.findViewById(R.id.allAppList);
        appCount = rootView.findViewById(R.id.allAppCount);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateAppList();
        initAppView();
        registerPackagesChanges();
    }

    public void populateAppList() {
        AsyncAppLoader loader = new AsyncAppLoader(this);
        loader.execute();
    }

    private void initAppView() {
        Context context = getContext();
        appAdapter = new AppAdapter(appList);
        appView.setLayoutManager(new LinearLayoutManager(context));
        appView.setHasFixedSize(true);
        appView.setAdapter(appAdapter);
    }

    void refresh(List<AppDetail> apps) {
        appList = apps;
        appAdapter.updateList(apps);
        StringBuilder builder = new StringBuilder("App count: ").append(appAdapter.getItemCount());
        appCount.setText(builder.toString());
        Toast.makeText(getContext(), "Finish loading", Toast.LENGTH_SHORT).show();
    }

    private void registerPackagesChanges() {
        registerPackageInstalled();
        registerPackagesRemoved();
        // appAdapter.notifyDataSetChanged();
    }

    private void registerPackagesRemoved() {
        appRemoveReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                Toast.makeText(context, "App removed: " + packageName1, Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter appRemoved = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        appRemoved.addDataScheme("package");
        getContext().registerReceiver(appRemoveReceiver, appRemoved);
    }

    private void registerPackageInstalled() {
        appInstallReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                Toast.makeText(context, "App installed: " + packageName, Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter appInstalled = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        appInstalled.addDataScheme("package");
        getContext().registerReceiver(appInstallReceiver, appInstalled);
    }


    @Override
    public void onDestroy() {
        unregisterPackagesChanges();
        super.onDestroy();
    }

    private void unregisterPackagesChanges() {
        getContext().unregisterReceiver(appInstallReceiver);
        getContext().unregisterReceiver(appRemoveReceiver);
    }
}
