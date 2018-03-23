package vn.ldbach.launcher.AppListFunction;

import android.content.Intent;

/**
 * Data class for application details
 */

public final class AppDetail {

    private String name;
    private Intent launchIntent;
    private String label;
    AppDetail(String appName, String appLabel, Intent appLaunchIntent) {
        name = appName;
        label = appLabel;
        launchIntent = appLaunchIntent;
    }

    public String getLabel() {return label;}
    public String getName() {
        return name;
    }
    Intent getLaunchIntent() {
        return launchIntent;
    }

    int compares(AppDetail app2) {
        return label.compareToIgnoreCase(app2.getLabel());
    }

    String getSearchName() {
        return label;
    }
}
