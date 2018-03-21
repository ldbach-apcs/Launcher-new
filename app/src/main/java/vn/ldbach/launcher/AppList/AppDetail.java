package vn.ldbach.launcher.AppList;

import android.content.Intent;

/**
 * Created by Duy-Bach on 3/18/2018.
 */

public final class AppDetail {

    AppDetail(String appName, String appLabel, Intent appLaunchIntent) {
        name = appName;
        label = appLabel;
        launchIntent = appLaunchIntent;
    }

    private String name;
    private Intent launchIntent;
    private String label;

    public String getLabel() {return label;}
    public String getName() {
        return name;
    }
    Intent getLaunchIntent() {
        return launchIntent;
    }
}
