package com.dod.DOD_ServiceProviders.ui.allorders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.dod.DOD_ServiceProviders.R;
import com.google.android.material.tabs.TabLayout;

public class AllOrders extends Fragment {
    SectionPageAdapter_orders sectionPageAdapter;
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allorders, container, false);
        FragmentManager fragmentManager = getChildFragmentManager() ;
        tabLayout = view.findViewById(R.id.tablayout);
        sectionPageAdapter = new SectionPageAdapter_orders(fragmentManager, tabLayout.getTabCount(),container.getContext());
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //Toast.makeText(getActivity().getApplicationContext(),"All orders",Toast.LENGTH_SHORT).show();
        return view;
    }
}