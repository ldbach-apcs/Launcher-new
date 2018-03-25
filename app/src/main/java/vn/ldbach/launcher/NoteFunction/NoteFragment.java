package vn.ldbach.launcher.NoteFunction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import vn.ldbach.launcher.LauncherFragment;
import vn.ldbach.launcher.R;

/**
 * Fragment for containing note function
 */

public final class NoteFragment extends LauncherFragment {
    EditText noteInput;
    ProgressBar progressBar;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_simple_note, container, false);
        noteInput = rootView.findViewById(R.id.noteInput);
        progressBar = rootView.findViewById(R.id.noteProgressBar);
        enableAutoSaveWhenNotTyping();
        return rootView;
    }

    private void enableAutoSaveWhenNotTyping() {
        noteInput.addTextChangedListener(new TextWatcher() {
            private final long DELAY = 500; // milliseconds
            private final Runnable runnable = () -> saveNote();
            Handler handler = new Handler();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, DELAY);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadNote();
    }

    private void loadNote() {
        new AsyncNoteLoader(this).execute();
    }

    @Override
    public void onStop() {
        saveNote();
        super.onStop();
    }

    private void saveNote() {
        new AsyncNoteSaver(this).execute(String.valueOf(noteInput.getText()));
    }

    void onLoadSuccessfully(String note) {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
        noteInput.setVisibility(View.VISIBLE);
        noteInput.setText(note);
        noteInput.setSelection(note.length());
    }

    public void onLoading() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentEnter() {
        if (noteInput == null) return;
        noteInput.setSelection(noteInput.getText().length());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(noteInput.getWindowToken(), 0);
    }

    @Override
    public void onFragmentExit() {

    }
}
