package com.dod.DOD_ServiceProviders.ui.allorders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dod.DOD_ServiceProviders.R;

public class SectionPageAdapter_orders extends FragmentPagerAdapter {

    FragmentManager fragMan;
    int no_of_tabs;
    Context mContext;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.pending_oders, R.string.accepted_orders,R.string.completed_orders};
    public SectionPageAdapter_orders(@NonNull FragmentManager fm, int no_of_tabs,Context context) {
        super(fm);
        this.no_of_tabs = no_of_tabs;
        this.mContext=context;
        fragMan = fm;
    }

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
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public long getItemId(int position) {
         return position;
    }

    @Override
    public int getCount() {
        return no_of_tabs;
    }
}
