package vn.ldbach.launcher.NoteFunction;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Save notes
 */

class AsyncNoteSaver extends AsyncTask<String, Void, Void> {
    static final String FILE_NAME = "note.txt";
    private NoteFragment mFragment;

    AsyncNoteSaver(NoteFragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    protected Void doInBackground(String... strings) {
        writeToFile(strings[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mFragment = null;
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(mFragment.getContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            // empty
        }
    }
}
