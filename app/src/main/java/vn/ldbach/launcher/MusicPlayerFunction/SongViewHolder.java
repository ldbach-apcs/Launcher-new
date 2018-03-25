package vn.ldbach.launcher.MusicPlayerFunction;

import android.support.v7.widget.RecyclerView;

import vn.ldbach.launcher.LauncherFragment;
import vn.ldbach.launcher.databinding.ItemSongBinding;

/**
 * Music ViewHolder
 */

class SongViewHolder extends RecyclerView.ViewHolder {
    private final ItemSongBinding binding;
    private final LauncherFragment fragment;

    SongViewHolder(ItemSongBinding binding, LauncherFragment fragment) {
        super(binding.getRoot());
        this.binding = binding;
        this.fragment = fragment;
    }

    void bind(Song song) {
        binding.setSong(song);
        itemView.setOnClickListener(new SongItemClickListener(fragment, song));
        binding.executePendingBindings();
    }
}
