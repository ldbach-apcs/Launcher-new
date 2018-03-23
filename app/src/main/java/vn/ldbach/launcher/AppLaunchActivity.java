package vn.ldbach.launcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import vn.ldbach.launcher.AppListFunction.AppListFragment;
import vn.ldbach.launcher.NoteFunction.NoteFragment;

public class AppLaunchActivity extends AppCompatActivity {

    /*
     * Handle ViewPager
     */

    private static final int NUM_PAGES = 2; // later will load from settings
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launch);
        mPager = findViewById(R.id.viewPager);
        PagerAdapter mPagerAdapter = new AppFragmentPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button.
            super.onBackPressed();
        } else {
            // Otherwise, return to AppList activity.
            mPager.setCurrentItem(0);
        }
    }

    private class AppFragmentPagerAdapter extends FragmentStatePagerAdapter {
        AppFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new NoteFragment();
                default:
                    return new AppListFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
