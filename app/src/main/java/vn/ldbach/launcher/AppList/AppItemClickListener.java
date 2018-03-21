package vn.ldbach.launcher.AppList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Duy-Bach on 3/20/2018.
 */

public final class AppItemClickListener implements View.OnClickListener {
    private Fragment fragment;
    private AppDetail app;

    AppItemClickListener(Fragment fragment, AppDetail app) {
        // Launch app
        this.fragment = fragment;
        this.app = app;
    }

    @Override
    public void onClick(View view) {
        launchApp();
    }


    private void launchApp() {
        Intent launchIntent = app.getLaunchIntent();
        fragment.getContext().startActivity(launchIntent);
    }
}
