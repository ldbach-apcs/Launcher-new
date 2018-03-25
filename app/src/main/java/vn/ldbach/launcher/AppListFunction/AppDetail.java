package vn.ldbach.launcher.AppListFunction;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Data class for application details
 */

public final class AppDetail {

    private String name;
    private Intent launchIntent;
    private String label;
    private Drawable icon;

    AppDetail(String appName, String appLabel, Drawable appIcon, Intent appLaunchIntent) {
        name = appName;
        label = appLabel;
        launchIntent = appLaunchIntent;
        icon = appIcon;
    }

    public Drawable getIcon() {
        return icon;
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
