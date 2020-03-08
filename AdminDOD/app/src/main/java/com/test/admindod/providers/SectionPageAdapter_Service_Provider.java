package com.test.admindod.providers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.test.admindod.R;

public class SectionPageAdapter_Service_Provider extends FragmentPagerAdapter {

    FragmentManager fragMan;
    Context mContext;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_5, R.string.tab_text_6};

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Available_Providers();
            case 1:
                return new Service_provider_add_new();
            default:
                return null;
        }
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    public SectionPageAdapter_Service_Provider(@NonNull FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext=mContext;
        fragMan = fm;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
