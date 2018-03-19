package vn.ldbach.launcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppLaunchActivity extends AppCompatActivity {

    /*
     * AppLaunchActivity: Contains only a search bar for
     * searching application and contacts. All the lists
     * will be handled inside fragments.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launch);
    }
}
