package vn.ldbach.launcher.AppListFunction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Load list of application in background
 */
class AsyncAppLoader extends AsyncTask<Void, Void, List<AppDetail>> {
    private Fragment loaderFragment = null;

    AsyncAppLoader(Fragment fragment) {
        loaderFragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<AppDetail> doInBackground(Void... voids) {
        final PackageManager pm = loaderFragment.getContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> packages = pm.queryIntentActivities(intent, 0);
        List<AppDetail> apps = new ArrayList<>();
        for (ResolveInfo packageInfo : packages) {
            String appLabel = packageInfo.loadLabel(pm).toString();
            String appName = packageInfo.activityInfo.packageName;
            Intent appIntent = pm.getLaunchIntentForPackage(packageInfo.activityInfo.packageName);
            Drawable appIcon = packageInfo.activityInfo.loadIcon(pm);
            AppDetail app = new AppDetail(appName, appLabel, appIcon, appIntent);
            apps.add(app);
        }
        Collections.sort(apps, AppDetail::compares);
        return apps;
    }

    @Override
    protected void onPostExecute(List<AppDetail> appDetails) {
        ((AppListFragment) loaderFragment).refresh(appDetails);
    }
}
