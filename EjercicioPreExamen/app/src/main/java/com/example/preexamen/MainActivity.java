package com.example.preexamen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    JugadoresVM vm;
    ListView lv;
    AdapterJugador adapter;
    private String IntentPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        vm = ViewModelProviders.of(this).get(JugadoresVM.class);

        lv = findViewById(android.R.id.list);

        adapter = new AdapterJugador(this, vm.getJugadores().getValue());

        final androidx.lifecycle.Observer<ArrayList<Object>> observerTestList = new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Object> tests) {
                adapter.notifyDataSetChanged();
            }
        };

        vm.getJugadores().observe(this, observerTestList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object Jugador;
                Jugador = adapterView.getItemAtPosition(position);
                if(Jugador instanceof clsJugadorFutbol){
                    clsJugadorFutbol futbolista = (clsJugadorFutbol) Jugador;
                    Intent intent = new Intent(MainActivity.this,ActivityEditarFutbolista.class);
                    intent.putExtra("futbolista",futbolista);
                    startActivity(intent);
                }else{
                    clsJugadorBaloncesto baloncestista = (clsJugadorBaloncesto) Jugador;
                    Intent intent = new Intent(MainActivity.this,ActivityEditarBaloncestista.class);
                    intent.putExtra("baloncestista",baloncestista);
                    startActivity(intent);
                }
            }
        });

        Intent intent = getIntent();

        if(intent!=null) {
            IntentPackage = intent.getPackage();
            if(IntentPackage == null){
                IntentPackage = "";
            }
            if (IntentPackage.equals("guardar_f")) {

            } else if (IntentPackage.equals("borrar_f")) {

            } else if (IntentPackage.equals("clonar_f")) {

            }
            if (IntentPackage.equals("guardar_b")) {
                clsJugadorBaloncesto jb = (clsJugadorBaloncesto) intent.getParcelableExtra("guardar_b");
                ArrayList<Object> listado = new ArrayList<Object>();
                listado = vm.getJugadores().getValue();
                for (int i = 0; i < listado.size(); i++) {
                    if (((clsJugadorBaloncesto) listado.get(i)).getId() == jb.getId()) {
                        ((clsJugadorBaloncesto) listado.get(i)).setAsistenciasPorPartido(jb.getAsistenciasPorPartido());
                        ((clsJugadorBaloncesto) listado.get(i)).setFoto(jb.getFoto());
                        ((clsJugadorBaloncesto) listado.get(i)).setPuntosPorPartido(jb.getPuntosPorPartido());
                        ((clsJugadorBaloncesto) listado.get(i)).setNombre(jb.getNombre());
                        ((clsJugadorBaloncesto) listado.get(i)).setRebotesPorPartido(jb.getRebotesPorPartido());
                    }
                }
            } else if (IntentPackage.equals("borrar_b")) {
                clsJugadorBaloncesto jb = (clsJugadorBaloncesto) intent.getParcelableExtra("borrar_b");
                for (int i = 0; i < vm.getJugadores().getValue().size(); i++) {
                    if (vm.getJugadores().getValue().get(i) instanceof clsJugadorBaloncesto && ((clsJugadorBaloncesto)vm.getJugadores().getValue().get(i)).getId() == jb.getId()) {
                        vm.getJugadores().getValue().remove(i);
                    }
                }
            } else if (IntentPackage.equals("clonar_b")) {
                clsJugadorBaloncesto jb = (clsJugadorBaloncesto) intent.getParcelableExtra("clonar_b");
                ArrayList<Object> listado = new ArrayList<Object>();
                listado = vm.getJugadores().getValue();
                listado.add(jb);
            }
        }
    }



}
