package com.test.admindod.providers;

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

public class Servie_Providers extends Fragment {
    SectionPageAdapter_Service_Provider sectionPageAdapter;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_service_providers, container, false);
        FragmentManager fragmentManager=getChildFragmentManager();
        tabLayout=view.findViewById(R.id.provider_tablayout);
        sectionPageAdapter=new SectionPageAdapter_Service_Provider(fragmentManager,container.getContext());
        ViewPager viewPager=view.findViewById(R.id.provider_viewpager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
