package com.syc.ui.main;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.syc.R;
import com.syc.ui.BusinessFragment;
import com.syc.ui.MostPopularFragment;
import com.syc.ui.TopStoriesFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_top_stories, R.string.tab_most_popular,R.string.tab_business};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        //TODO: méthode deprecated !!
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                return TopStoriesFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return BusinessFragment.newInstance();
            default:
                return PlaceholderFragment.newInstance(position);
        }

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}