package com.test.admindod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {

    FragmentManager fragMan;
    int no_of_tabs;

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new orders_pending();
            case 1:
                return new orders_accepted();
            case 2:
                return new orders_completed();
            case 3:
                return new orders_canceled();
            default:
                return null;
        }
    }

    public SectionPageAdapter(@NonNull FragmentManager fm, int no_of_tabs) {
        super(fm);
        this.no_of_tabs = no_of_tabs;
        fragMan = fm;
    }

    @Override
    public int getCount() {
        return no_of_tabs;
    }
}
