package com.example.buscaminas.Play;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.buscaminas.Activities.JuegoActivity;
import com.example.buscaminas.Adapters.AutoScrollPagerAdapter;
import com.example.buscaminas.Adapters.AutoScrollViewPager;
import com.example.buscaminas.R;
import com.google.android.material.tabs.TabLayout;

public class PlayFragment extends Fragment {

    private Button empezarJuego;
    private String[] dificultades = new String[] {"Nivel Facil", "Nivel Medio", "Nivel Dificil", "Nivel Extremo"};
    private Spinner spinner;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 1000;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_play, container, false);
        spinner = root.findViewById(R.id.spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,dificultades);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        AutoScrollPagerAdapter autoScrollPagerAdapter = new AutoScrollPagerAdapter(getActivity().getSupportFragmentManager());
        AutoScrollViewPager viewPager = root.findViewById(R.id.pager);
        viewPager.setAdapter(autoScrollPagerAdapter);

        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        viewPager.startAutoScroll();

        viewPager.setInterval(AUTO_SCROLL_THRESHOLD_IN_MILLI);

        viewPager.setCycle(true);


        empezarJuego = root.findViewById(R.id.btnJugar);

        empezarJuego.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JuegoActivity.class);
                intent.putExtra("dificultad",(String)spinner.getSelectedItem());
                startActivity(intent);
                getActivity().overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
            }
        });



        return root;

    }


}