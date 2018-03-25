package vn.ldbach.launcher.MusicPlayerFunction;

import android.view.View;

import vn.ldbach.launcher.LauncherFragment;

/**
 *
 */

class SongItemClickListener implements View.OnClickListener {
    private LauncherFragment fragment;
    private Song song;

    SongItemClickListener(LauncherFragment fragment, Song song) {
        this.fragment = fragment;
        this.song = song;
    }

    @Override
    public void onClick(View view) {
        MusicService ms = ((MusicFragment) fragment).getMusicService();
        ms.setSong(song);
        ms.playSong();
    }
}
