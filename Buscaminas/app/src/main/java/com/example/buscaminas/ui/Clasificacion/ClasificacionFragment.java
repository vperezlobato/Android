package com.example.buscaminas.ui.Clasificacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.buscaminas.Adapters.listaClasificacionAdapter;
import com.example.buscaminas.Clases.Partida;
import com.example.buscaminas.R;

import java.util.ArrayList;

public class ClasificacionFragment extends Fragment {

    private ListView lista;
    private ClasificacionVM vm;
    private listaClasificacionAdapter adapter;
    private RadioGroup RGfiltraDificultad;
    private RadioButton RBFacil;
    private RadioButton RBMedio;
    private RadioButton RBDificil;
    private RadioButton RBExtremo;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clasificacion, container, false);
        lista = root.findViewById(R.id.lista);
        RGfiltraDificultad =root.findViewById(R.id.RGFiltrarDificultad);
        RBFacil = root.findViewById(R.id.Facil);
        RBMedio = root.findViewById(R.id.Medio);
        RBDificil = root.findViewById(R.id.Dificil);
        RBExtremo = root.findViewById(R.id.Extremo);

        vm = ViewModelProviders.of(this).get(ClasificacionVM.class);

        //Creamos el Observer
        androidx.lifecycle.Observer<ArrayList<Partida>> listaObserver = new Observer<ArrayList<Partida>>() {
            @Override
            public void onChanged(ArrayList<Partida> listContact) {
                //Actualizar la UI
                lista.invalidate();
                adapter = new listaClasificacionAdapter(getActivity(), vm.getListaPartidas().getValue() );
                lista.setAdapter(adapter);
            }
        };

        vm.getListaPartidas().observe(getActivity(), listaObserver);

        androidx.lifecycle.Observer<ArrayList<Partida>> lista2Observer = new Observer<ArrayList<Partida>>() {
            @Override
            public void onChanged(ArrayList<Partida> listContact) {
                //Actualizar la UI
                vm.getListaPartidasAux().getValue();
                vm.filtrarPorDificultad(vm.getDificultad());
            }
        };
        vm.getListaPartidasAux().observe(getActivity(), lista2Observer);

        RadioGroup.OnCheckedChangeListener radioButtonListener = new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (RGfiltraDificultad.getCheckedRadioButtonId()) {
                    case R.id.Facil:
                        vm.filtrarPorDificultad("Nivel Facil");
                        vm.setDificultad("Nivel Facil");//esto se lo añadi para que si dejas pulsado el checkbox
                                                        // y juegas una partida se realice el cambio en la lista y se pueda ver reflejado al cambiar de fragment
                        break;
                    case R.id.Medio:
                        vm.filtrarPorDificultad("Nivel Medio");
                        vm.setDificultad("Nivel Medio");
                        break;
                    case R.id.Dificil:
                        vm.filtrarPorDificultad("Nivel Dificil");
                        vm.setDificultad("Nivel Dificil");
                        break;
                    case R.id.Extremo:
                        vm.filtrarPorDificultad("Nivel Extremo");
                        vm.setDificultad("Nivel Extremo");
                        break;
                }
            }
        };
        RGfiltraDificultad.setOnCheckedChangeListener(radioButtonListener);

        return root;
    }
}