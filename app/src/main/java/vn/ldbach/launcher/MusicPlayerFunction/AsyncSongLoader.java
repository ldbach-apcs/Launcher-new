package vn.ldbach.launcher.MusicPlayerFunction;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Duy-Bach on 3/24/2018.
 */

public class AsyncSongLoader extends AsyncTask<Void, Void, List<Song>> {
    MusicFragment fragment;

    AsyncSongLoader(MusicFragment fragment) {
        this.fragment = fragment;
    }


    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
        Collections.sort(songs, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        fragment.onLoadSuccessfully(songs);
    }

    @Override
    protected List<Song> doInBackground(Void... voids) {
        List<Song> songList = new ArrayList<>();
        ContentResolver musicResolver = fragment.getActivity().getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());

            musicCursor.close();
        }
        return songList;
    }
}
