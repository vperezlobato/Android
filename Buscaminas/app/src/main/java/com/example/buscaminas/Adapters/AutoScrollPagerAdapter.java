package com.example.buscaminas.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.buscaminas.Slider.SlideFragment;

public class AutoScrollPagerAdapter extends FragmentPagerAdapter {
    public AutoScrollPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        // Return a SlideFragment (defined as a static inner class below).
        return SlideFragment.newInstance(position + 1);
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}