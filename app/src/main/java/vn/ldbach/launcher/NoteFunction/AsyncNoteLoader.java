package vn.ldbach.launcher.NoteFunction;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Load notes
 */

public final class AsyncNoteLoader extends AsyncTask<Void, Void, String> {

    private NoteFragment mFragment;

    AsyncNoteLoader(NoteFragment fragment) {
        mFragment = fragment;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return readFromFile(mFragment.getContext());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mFragment.onLoading();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mFragment.onLoadSuccessfully(s);
        mFragment = null;
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(AsyncNoteSaver.FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            // Do nothing, first run no file there
        } catch (IOException e) {
            // error reading
            return "";
        }

        return ret;
    }
}
