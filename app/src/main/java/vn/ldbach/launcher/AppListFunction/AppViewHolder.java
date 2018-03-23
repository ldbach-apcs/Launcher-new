package vn.ldbach.launcher.AppListFunction;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import vn.ldbach.launcher.databinding.ItemAppBinding;

/**
 * ViewHolder for application in Launcher main
 */

class AppViewHolder extends RecyclerView.ViewHolder {
    private final ItemAppBinding binding;
    private final Fragment fragment;

    AppViewHolder(final ItemAppBinding itemBinding, Fragment fragment) {
        super(itemBinding.getRoot());
        this.fragment = fragment;
        binding = itemBinding;
    }


    void bind(AppDetail app) {
        binding.setApp(app);
        itemView.setOnClickListener(new AppItemClickListener(fragment, app));
        itemView.setOnLongClickListener(new AppItemLongClickListener(fragment, app));
        binding.executePendingBindings();
    }
}
