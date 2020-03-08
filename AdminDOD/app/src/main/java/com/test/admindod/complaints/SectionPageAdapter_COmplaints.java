package com.test.admindod.complaints;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.test.admindod.R;

public class SectionPageAdapter_COmplaints extends FragmentPagerAdapter {

    FragmentManager fragMan;
   Context mContext;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.customers,R.string.service_providers};

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Customers_Complaints customers_complaints=new Customers_Complaints();
                return customers_complaints;
            case 1:
                ProviderComplaints providerComplaints=new ProviderComplaints();
                return providerComplaints;
            default:
                return null;
        }
    }

    public SectionPageAdapter_COmplaints(@NonNull FragmentManager fm, Context mContext) {
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
        return 2;
    }
}
