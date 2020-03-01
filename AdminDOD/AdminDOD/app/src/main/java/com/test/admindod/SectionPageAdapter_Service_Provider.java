package com.test.admindod;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPageAdapter_Service_Provider extends FragmentPagerAdapter {

    FragmentManager fragMan;
    int no_of_tabs;

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Service_provider_online();
            case 1:
                return new Service_provider_offline();
            case 2:
                return new Service_provider_add_new();
            default:
                return null;
        }
    }

    public SectionPageAdapter_Service_Provider(@NonNull FragmentManager fm, int no_of_tabs) {
        super(fm);
        this.no_of_tabs = no_of_tabs;
        fragMan = fm;
    }

    @Override
    public int getCount() {
        return no_of_tabs;
    }
}
