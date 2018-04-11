package vn.ldbach.launcher.MusicPlayerFunction;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Play music with service
 */

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener  {

    private MediaPlayer player;
    private List<Song> songs;
    private int songPos;
    private final IBinder musicBind = new MusicBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        songPos = 0;
        initMusicPlayer();
    }

    private void initMusicPlayer() {
        player = new MediaPlayer();
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setList(List<Song> songs){
        this.songs = songs;
    }

    public void setSong(Song song){
        songPos = songs.indexOf(song);
    }

    public void playSong() {
        player.reset();
        //get song
        Song playSong = songs.get(songPos);
        long currSong = playSong.getID();
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        try {
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }


        player.prepareAsync();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        player = mediaPlayer;
        player.start();
    }

    public void endSong() {
        player.stop();
    }

    class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
}
