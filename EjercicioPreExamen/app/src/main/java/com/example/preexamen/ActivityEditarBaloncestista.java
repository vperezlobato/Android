package com.example.preexamen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityEditarBaloncestista extends AppCompatActivity {

    private ImageView Foto;
    private EditText Nombre, Puntos, Rebotes, Asistencias;
    private Button Borrar, Clonar, Guardar;

    private Switch switchEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_baloncesto);

        Borrar=(Button)findViewById(R.id.btnBorrar);
        Clonar=(Button)findViewById(R.id.btnClonar);
        Guardar=(Button)findViewById(R.id.btnGuardar);

        Foto = (ImageView)findViewById(R.id.imgFoto);
        Nombre=(EditText)findViewById(R.id.etxNombre);
        Puntos=(EditText)findViewById(R.id.etxPuntos);
        Rebotes=(EditText)findViewById(R.id.etxRebotes);
        Asistencias=(EditText)findViewById(R.id.etxAsistencias);

        switchEditar=(Switch)findViewById(R.id.swtEditar);
        switchEditar.setChecked(false);

        switchEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivarCampos();
            }
        });

        Intent  i = getIntent();
        final clsJugadorBaloncesto jb = i.getParcelableExtra("baloncestista");
        mostrarIntentRecibido(i);

        switchActivarCampos();

        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(ActivityEditarBaloncestista.this,MainActivity.class);
                intent.putExtra("borrar_b",jb);
                intent.setPackage("borrar_b");
                startActivity(intent);
            }
        });

        Clonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ActivityEditarBaloncestista.this,MainActivity.class);
                intent.putExtra("clonar_b",jb);
                intent.setPackage("clonar_b");
                startActivity(intent);
            }
        });

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ActivityEditarBaloncestista.this,MainActivity.class);
                intent.putExtra("guardar_b",jb);
                intent.setPackage("guardar_b");
                startActivity(intent);
            }
        });

    }

    private void switchActivarCampos() {

        Nombre.setEnabled(!Nombre.isEnabled());
        Foto.setEnabled(!Foto.isEnabled());
        Puntos.setEnabled(!Puntos.isEnabled());
        Rebotes.setEnabled(!Rebotes.isEnabled());
        Asistencias.setEnabled(!Asistencias.isEnabled());
    }

    private void mostrarIntentRecibido(Intent i) {

        clsJugadorBaloncesto jb = i.getParcelableExtra("baloncestista");
        Foto.setImageResource(jb.getFoto());
        Nombre.setText(jb.getNombre());
        Puntos.setText(String.valueOf(jb.getPuntosPorPartido()));
        Rebotes.setText(String.valueOf(jb.getRebotesPorPartido()));
        Asistencias.setText(String.valueOf(jb.getAsistenciasPorPartido()));
    }

}
