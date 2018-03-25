package vn.ldbach.launcher.AppListFunction;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ProgressBar;

import java.util.List;

import vn.ldbach.launcher.LauncherFragment;
import vn.ldbach.launcher.R;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Fragment for displaying list of applications
 */

public final class AppListFragment extends LauncherFragment {

    RecyclerView appView = null;
    List<AppDetail> appList = null;
    AppListAdapter appListAdapter;
    private BroadcastReceiver appInstallReceiver, appRemoveReceiver;
    private EditText searchAppBox;
    private ProgressBar progressBar;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_list, container, false);
        appView = rootView.findViewById(R.id.allAppList);
        searchAppBox = rootView.findViewById(R.id.searchAppBox);
        progressBar = rootView.findViewById(R.id.progressBar);
        setupInteraction();
        return rootView;
    }

    private void setupInteraction() {
        searchAppBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                refreshListPosition();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterApps(editable.toString());
            }
        });

        //noinspection deprecation
        appView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) return;
                searchAppBox.clearFocus();
                hideKeyboard();
            }
        });
    }

    private void filterApps(String s) {
        if (appListAdapter != null) {
            Filter appFilter = appListAdapter.getFilter();
            appFilter.filter(s);
        }
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
        appListAdapter = new AppListAdapter(appList, this);
        appView.setLayoutManager(new LinearLayoutManager(context));
        appView.setHasFixedSize(true);
        appView.setAdapter(appListAdapter);
    }

    void refresh(List<AppDetail> apps) {
        appList = apps;
        appListAdapter.updateList(apps);
        progressBar.setVisibility(View.GONE);
        appView.setVisibility(View.VISIBLE);
        // showKeyboard();
    }

    private void registerPackagesChanges() {
        registerPackageInstalled();
        registerPackagesRemoved();
    }

    private void registerPackagesRemoved() {
        appRemoveReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                @SuppressWarnings("ConstantConditions") // null check below so no worries
                final String packageName = intent.getData().getEncodedSchemeSpecificPart();

                if (packageName != null) {
                    for (AppDetail app : appList) {
                        if (!app.getName().equals(packageName)) {
                            appList.remove(app);
                            break;
                        }
                    }
                }
                refresh(appList);
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
                @SuppressWarnings("ConstantConditions") // null check below so no worries
                        String packageName = intent.getData().getEncodedSchemeSpecificPart();

                if (packageName != null) {
                    try {
                        final PackageManager pm = getContext().getPackageManager();
                        ApplicationInfo app = pm.getApplicationInfo(packageName, 0);
                        String appLabel = app.loadLabel(pm).toString();
                        String appName = app.packageName;
                        Intent appIntent = pm.getLaunchIntentForPackage(app.packageName);
                        Drawable appIcon = app.loadIcon(pm);
                        appList.add(new AppDetail(appName, appLabel, appIcon, appIntent));
                    } catch (PackageManager.NameNotFoundException e) {
                        // Do nothing
                    }
                    refresh(appList);
                }
            }
        };
        IntentFilter appInstalled = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        appInstalled.addDataScheme("package");
        getContext().registerReceiver(appInstallReceiver, appInstalled);
    }

    @Override
    public void onStop() {
        unregisterPackagesChanges();
        super.onStop();
    }

    private void unregisterPackagesChanges() {
        getContext().unregisterReceiver(appInstallReceiver);
        getContext().unregisterReceiver(appRemoveReceiver);
    }

    private void refreshListPosition() {
        appView.scrollToPosition(0);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    private void showKeyboard() {
        Activity a = getActivity();
        if (a == null) return;
        InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(searchAppBox.getWindowToken(), 0);
    }

    @Override
    public void onFragmentEnter() {
        showKeyboard();
    }

    @Override
    public void onFragmentExit() {

    }
}
