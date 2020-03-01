package com.easysolutions.dod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class SectionPAdapter extends FragmentPagerAdapter {

    private  List<Fragment> mFragmentList=new ArrayList<>();
    private  List<String> mFragmentTitleList=new ArrayList<>();
    FragmentManager fragMan;

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);

    }

    public SectionPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragMan=fm;
    }
    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
