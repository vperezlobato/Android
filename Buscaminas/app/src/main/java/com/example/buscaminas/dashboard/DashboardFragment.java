package com.example.buscaminas.dashboard;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.buscaminas.R;

public class DashboardFragment extends Fragment {

    private Button empezarJuego;
    private String[] dificultades = new String[] {"Nivel Facil", "Nivel Medio", "Nivel Dificil", "Nivel Extremo"};
    private Spinner spinner;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        spinner = (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_dificultad,dificultades);
        spinner.setAdapter(adapter);

        empezarJuego = root.findViewById(R.id.btnJugar);
        return root;

    }
}