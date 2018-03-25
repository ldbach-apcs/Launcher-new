package vn.ldbach.launcher.MusicPlayerFunction;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import vn.ldbach.launcher.LauncherFragment;
import vn.ldbach.launcher.R;

/**
 * Music player fragment
 */

public class MusicFragment extends LauncherFragment {
    SongAdapter adapter;
    Button stopMusic;
    private RecyclerView songView = null;
    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;
    private List<Song> songs;
    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            //get service
            musicService = binder.getService();
            //pass list
            musicService.setList(songs);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_player, container, false);
        songView = rootView.findViewById(R.id.musicList);
        stopMusic = rootView.findViewById(R.id.stopMusic);
        adapter = new SongAdapter(this, null);
        songView.setAdapter(adapter);
        songView.setLayoutManager(new LinearLayoutManager(getContext()));
        songView.setHasFixedSize(true);
        initInteraction();
        return rootView;
    }

    private void initInteraction() {
        stopMusic.setOnClickListener(v -> {
            endSong();
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        new AsyncSongLoader(this).execute();
        if (playIntent == null) {
            playIntent = new Intent(getContext(), MusicService.class);
            Activity a = getActivity();
            if (a != null) {
                a.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
                a.startService(playIntent);
            }
        }
    }

    @Override
    public void onFragmentEnter() {
    }

    @Override
    public void onFragmentExit() {

    }

    public void onLoadSuccessfully(List<Song> songs) {
        // inflate songs
        this.songs = songs;
        adapter.updateList(songs);
        musicService.setList(songs);
    }

    public MusicService getMusicService() {
        return musicService;
    }

    public void endSong() {
        musicService.endSong();
    }

    @Override
    public void onDestroy() {
        endSong();
        super.onDestroy();
    }
}
