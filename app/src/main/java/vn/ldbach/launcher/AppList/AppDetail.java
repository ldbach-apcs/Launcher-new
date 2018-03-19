package vn.ldbach.launcher.AppList;

import android.content.Intent;

/**
 * Created by Duy-Bach on 3/18/2018.
 */

public final class AppDetail {

    AppDetail(String appName, Intent appLaunchIntent) {
        name = appName;
        launchIntent = appLaunchIntent;
    }

    private String name;
    private Intent launchIntent;

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public Intent getLaunchIntent() {
        return launchIntent;
    }
}
