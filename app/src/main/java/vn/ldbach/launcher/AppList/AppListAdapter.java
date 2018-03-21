package vn.ldbach.launcher.AppList;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.ldbach.launcher.databinding.ItemAppBinding;

/**
 * Adapter for displaying list of applications
 */

class AppListAdapter extends RecyclerView.Adapter<AppViewHolder> {
    private List<AppDetail> apps;
    private Fragment fragment;
    AppListAdapter(List<AppDetail> appDetails, Fragment fragment) {
        apps = appDetails;
        this.fragment = fragment;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppBinding appBinding = ItemAppBinding.inflate(layoutInflater, parent, false);
        return new AppViewHolder(appBinding, fragment);
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        AppDetail app = apps.get(position);
        holder.bind(app);
    }

    @Override
    public int getItemCount() {
        if (apps == null) return 0;
        return apps.size();
    }

    void updateList(List<AppDetail> appList) {
        apps = appList;
        notifyDataSetChanged();
    }
}
