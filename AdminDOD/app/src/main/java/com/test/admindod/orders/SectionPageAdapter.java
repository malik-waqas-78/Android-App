package com.test.admindod.orders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.test.admindod.R;

public class SectionPageAdapter extends FragmentPagerAdapter {

    FragmentManager fragMan;
   Context mContext;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4};

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                orders_pending orders_pending=new orders_pending();
                return orders_pending;
            case 1:
                orders_accepted orders_accepted=new orders_accepted();
                return orders_accepted;
            case 2:
                orders_completed orders_completed=new orders_completed();
                return orders_completed;
            case 3:
                orders_canceled orders_canceled=new orders_canceled();
                return orders_canceled;
            default:
                return null;
        }
    }

    public SectionPageAdapter(@NonNull FragmentManager fm, Context mContext) {
        super(fm);
        fragMan = fm;
        this.mContext=mContext;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}
