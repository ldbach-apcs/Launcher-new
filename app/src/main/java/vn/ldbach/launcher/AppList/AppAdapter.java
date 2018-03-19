package vn.ldbach.launcher.AppList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.ldbach.launcher.databinding.ItemAppBinding;

/**
 * Created by Duy-Bach on 3/18/2018.
 */

class AppAdapter extends RecyclerView.Adapter<AppViewHolder> {
    private List<AppDetail> apps;
    AppAdapter(List<AppDetail> appDetails) {
        apps = appDetails;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppBinding appBinding = ItemAppBinding.inflate(layoutInflater, parent, false);
        return new AppViewHolder(appBinding, parent.getContext());
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
