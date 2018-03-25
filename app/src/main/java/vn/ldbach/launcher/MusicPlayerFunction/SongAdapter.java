package vn.ldbach.launcher.MusicPlayerFunction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.ldbach.launcher.LauncherFragment;
import vn.ldbach.launcher.databinding.ItemSongBinding;

/**
 * Adapter for displaying songs
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private LauncherFragment fragment;
    private List<Song> songs;

    SongAdapter(LauncherFragment fragment, List<Song> songs) {
        this.fragment = fragment;
        this.songs = songs;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSongBinding songBinding = ItemSongBinding.inflate(layoutInflater, parent, false);
        return new SongViewHolder(songBinding, fragment);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        if (songs == null) return 0;
        return songs.size();
    }

    void updateList(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }
}
