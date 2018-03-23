package vn.ldbach.launcher.AppList;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.ldbach.launcher.databinding.ItemAppBinding;

/**
 * Adapter for displaying list of applications
 */

class AppListAdapter extends RecyclerView.Adapter<AppViewHolder> implements Filterable {
    private List<AppDetail> filteredApps;
    private List<AppDetail> allApps;
    private Fragment fragment;
    private Filter mFilter = new AppFilter();

    AppListAdapter(List<AppDetail> appDetails, Fragment fragment) {
        filteredApps = appDetails;
        allApps = filteredApps;
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
        AppDetail app = filteredApps.get(position);
        holder.bind(app);
    }

    @Override
    public int getItemCount() {
        if (filteredApps == null) return 0;
        return filteredApps.size();
    }

    void updateList(List<AppDetail> appList) {
        filteredApps = appList;
        allApps = filteredApps;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class AppFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence query) {
            List<AppDetail> filtered = new ArrayList<>();
            for (AppDetail app : allApps) {
                if (matchName(app.getSearchName(), query.toString())) {
                    filtered.add(app);
                }
            }

            if (query.length() == 0) filtered = allApps;

            FilterResults result = new FilterResults();
            result.values = filtered;
            result.count = filtered.size();
            return result;
        }

        private boolean matchName(String appLabel, String query) {
            int i = 0, j = 0;
            while (i < appLabel.length() && j < query.length()) {
                char queryChar = Character.toLowerCase(query.charAt(j));
                char nameChar = Character.toLowerCase(appLabel.charAt(i));
                if (queryChar == nameChar) {
                    j++;
                }
                i++;
            }
            return j == query.length();
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults == null) return;
            if (filterResults.count == 0) {
                filteredApps = new ArrayList<>();
                notifyDataSetChanged();
                return;
            }

            @SuppressWarnings("unchecked")
            List<AppDetail> filterRes = (List<AppDetail>) filterResults.values;

            if (filterRes != null) {
                Collections.sort(filterRes, AppDetail::compares);
                filteredApps = filterRes;
                notifyDataSetChanged();
            }
        }
    }
}
