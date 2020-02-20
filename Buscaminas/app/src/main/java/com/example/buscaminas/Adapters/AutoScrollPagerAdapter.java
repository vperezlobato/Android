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
        return SlideFragment.newInstance(position + 1);
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position){
        CharSequence secuencia = "";
        switch(position){
            case 0:
                secuencia = "Facil";
            break;
            case 1:
                secuencia = "Medio";
                break;
            case 2:
                secuencia = "Dificil";
                break;
            case 3:
                secuencia = "Extremo";
                break;
        }
        return secuencia;
    }
}