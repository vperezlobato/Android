package es.iesnervion.victor.nbabd.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import es.iesnervion.victor.nbabd.Adapter.AdaptadorDeEquipos;
import es.iesnervion.victor.nbabd.Clases.Equipo;
import es.iesnervion.victor.nbabd.ViewModels.MainActivityVM;
import es.iesnervion.victor.nbabd.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Equipo> equipos;
    private ListView list;
    private AdaptadorDeEquipos adaptador;
    private Button insertar;
    private MainActivityVM vm;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.lista);
        insertar = findViewById(R.id.insertarEquipo);

        vm = new ViewModelProvider(this).get(MainActivityVM.class);

        vm.getEquipos().observe(this,new Observer<List<Equipo>>()
        {
            @Override
            public void onChanged(@Nullable final List<Equipo> equipos)
            {
                list.setAdapter(new AdaptadorDeEquipos(getBaseContext(),R.layout.activity_listview_activity ,vm.getEquipos().getValue()));
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent equipoIntent = new Intent(MainActivity.this, datosEquipoActivity.class);
                Equipo equipo = (Equipo) list.getItemAtPosition(position);
                Bundle extras = new Bundle();
                extras.putInt("IDEquipo", equipo.getTeamId());
                equipoIntent.putExtras(extras);
                startActivity(equipoIntent);
            }
        });


        insertar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,activity_introducir_equipo.class);
        startActivity(intent);
    }
}