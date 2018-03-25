package vn.ldbach.launcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vn.ldbach.launcher.AppListFunction.AppListFragment;
import vn.ldbach.launcher.MusicPlayerFunction.MusicFragment;
import vn.ldbach.launcher.NoteFunction.NoteFragment;

public class AppLaunchActivity extends AppCompatActivity {

    /*
     * Handle ViewPager
     */

    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1112;
    List<LauncherFragment> launcherFragments = new ArrayList<>();
    private ViewPager mPager;
    private boolean canHaveMusicPlayer = false;
    private int startItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launch);
        askPermission();
        setupFragments();
        mPager = findViewById(R.id.viewPager);
        PagerAdapter mPagerAdapter = new AppFragmentPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(startItem);
    }

    private void setupFragments() {
        if (canHaveMusicPlayer) {
            launcherFragments.add(new MusicFragment());
            startItem = 1;
        }
        launcherFragments.add(new AppListFragment());
        launcherFragments.add(new NoteFragment());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            canHaveMusicPlayer = true;
        }
    }

    private void askPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            canHaveMusicPlayer = true;
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == startItem) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button.
            super.onBackPressed();
        } else {
            // Otherwise, return to AppList activity.
            mPager.setCurrentItem(startItem);
        }
    }

    private class AppFragmentPagerAdapter extends FragmentStatePagerAdapter {
        AppFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LauncherFragment selected;
            selected = launcherFragments.get(position);
            return selected;
        }

        @Override
        public int getCount() {
            return launcherFragments.size();
        }
    }

}
