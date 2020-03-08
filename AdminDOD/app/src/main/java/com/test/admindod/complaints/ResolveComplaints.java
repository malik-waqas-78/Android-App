package com.test.admindod.complaints;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.test.admindod.MainActivity;
import com.test.admindod.R;

public class ResolveComplaints extends Fragment {
    TabLayout tabLayout;
    SectionPageAdapter_COmplaints sectionPageAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_complaintresolver,container,false);
        FragmentManager fragmentManager=getChildFragmentManager();
        tabLayout=view.findViewById(R.id.tablayout);
        sectionPageAdapter=new SectionPageAdapter_COmplaints(fragmentManager,container.getContext());
        ViewPager viewPager=view.findViewById(R.id.viewpager);
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if(MainActivity.tab.equals("1")){
            viewPager.setCurrentItem(1);
        }
        return view;
    }
}
