package com.test.admindod.orders;

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
import com.test.admindod.R;

public class FragmentAllOrders extends Fragment {
    SectionPageAdapter sectionPageAdapter;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragemnt_all_orders, container, false);
        FragmentManager fragmentManager=getChildFragmentManager();
        tabLayout=view.findViewById(R.id.tablayout);
        sectionPageAdapter=new SectionPageAdapter(fragmentManager,container.getContext());
        ViewPager viewPager=view.findViewById(R.id.viewpager);
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
