package com.example.buscaminas.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.buscaminas.Activities.JuegoActivity;
import com.example.buscaminas.Activities.MainActivity;
import com.example.buscaminas.R;

public class DashboardFragment extends Fragment {

    private Button empezarJuego;
    private String[] dificultades = new String[] {"Nivel Facil", "Nivel Medio", "Nivel Dificil", "Nivel Extremo"};
    private Spinner spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        spinner = root.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,dificultades);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        empezarJuego = root.findViewById(R.id.btnJugar);

        empezarJuego.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JuegoActivity.class);
                intent.putExtra("dificultad",(String)spinner.getSelectedItem());
                startActivity(intent);
            }
        });



        return root;

    }


}