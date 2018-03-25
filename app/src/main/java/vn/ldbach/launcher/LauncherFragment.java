package vn.ldbach.launcher;

import android.support.v4.app.Fragment;

/**
 * Created by Duy-Bach on 3/18/2018.
 */

public abstract class LauncherFragment extends Fragment {
    public abstract void onFragmentEnter();

    public abstract void onFragmentExit();
}
