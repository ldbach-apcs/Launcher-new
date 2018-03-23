package vn.ldbach.launcher.AppListFunction;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import vn.ldbach.launcher.R;

/**
 * Handle long click event in launcher main
 */

public class AppItemLongClickListener implements View.OnLongClickListener {
    private Fragment fragment;
    private AppDetail app;
    private PopupMenu.OnMenuItemClickListener menuClick;

    AppItemLongClickListener(Fragment fragment, AppDetail app) {
        this.fragment = fragment;
        this.app = app;
        menuClick = menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.uninstallApp:
                    uninstallApplication();
                    return true;
                case R.id.viewInfoApp:
                    viewInfoApp();
                    return true;
                default: return false;
            }
        };
    }

    @Override
    public boolean onLongClick(View view) {
        ContextThemeWrapper wrapper = new ContextThemeWrapper(fragment.getContext(), R.style.PopupTheme);
        PopupMenu popup = new PopupMenu(wrapper, view);
        MenuInflater menuInflater = fragment.getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_app_list_long_click, popup.getMenu());
        popup.setOnMenuItemClickListener(menuClick);
        popup.show();
        return true;
    }


    private void uninstallApplication() {
        if (app == null) return;
        Uri uri = Uri.parse("package:" + app.getName());
        Intent uninstall = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, uri);
        fragment.getContext().startActivity(uninstall);
    }

    private void viewInfoApp() {
        if (app == null) return;
        Uri uri = Uri.parse("package:" + app.getName());
        Intent viewInfo = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        fragment.getContext().startActivity(viewInfo);
    }
}
