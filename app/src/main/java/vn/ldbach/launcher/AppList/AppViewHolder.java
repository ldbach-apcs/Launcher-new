package vn.ldbach.launcher.AppList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vn.ldbach.launcher.databinding.ItemAppBinding;

/**
 * Created by Duy-Bach on 3/18/2018.
 */

class AppViewHolder extends RecyclerView.ViewHolder {
    private final ItemAppBinding binding;

    AppViewHolder(final ItemAppBinding itemBinding, final Context context) {
        super(itemBinding.getRoot());
        View itemView = itemBinding.getRoot();
        binding = itemBinding;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDetail app = itemBinding.getApp();
                // Intent launchIntent = app.getLaunchIntent();
                // context.startActivity(launchIntent);
                    Uri uri = Uri.parse("package:" + app.getName());
                Intent uninstall = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, uri);
                context.startActivity(uninstall);
            }
        });
    }

    void bind(AppDetail app) {
        binding.setApp(app);
        binding.executePendingBindings();
    }
}
