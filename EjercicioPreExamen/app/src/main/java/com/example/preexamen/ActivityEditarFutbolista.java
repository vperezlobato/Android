package com.example.preexamen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityEditarFutbolista extends AppCompatActivity {

    private ImageView Foto;
    private EditText Nombre, etxNuevaPosicion;
    private Button Borrar, Clonar, Guardar,btnNuevaPosicion,btnGuardarNuevaPosicion;
    private TextView txvNuevaPosicion, Posicion;
    private Spinner spinner;
    private Switch switchEditar;
    private ArrayAdapter<String> adapSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_futbolista);

        Foto = findViewById(R.id.imgFoto);
        Nombre = findViewById(R.id.etxNombre);
        etxNuevaPosicion = findViewById(R.id.etxNuevaPosicion);
        Borrar = findViewById(R.id.btnBorrarF);
        Clonar = findViewById(R.id.btnClonar);
        Guardar = findViewById(R.id.btnGuardar);
        btnNuevaPosicion = findViewById(R.id.btnNuevaPosicion);
        btnGuardarNuevaPosicion = findViewById(R.id.btnGuardarNuevaPosicion);
        txvNuevaPosicion = findViewById(R.id.txvNuevaPosicion);
        Posicion = findViewById(R.id.txvPosicion);
        spinner = findViewById(R.id.spinner);
        switchEditar = findViewById(R.id.swtEditar);

        switchEditar.setChecked(false);

        switchEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivarCampos();
            }
        });

        final Intent intentRecibido = getIntent();

        mostrarIntentRecibido(intentRecibido);

        switchActivarCampos();

        btnNuevaPosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txvNuevaPosicion.setVisibility(v.VISIBLE);
                etxNuevaPosicion.setVisibility(v.VISIBLE);
                btnGuardarNuevaPosicion.setEnabled(!btnGuardarNuevaPosicion.isEnabled());
            }
        });

        btnGuardarNuevaPosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirPosicion(intentRecibido);
            }
        });

    }

    public void añadirPosicion(Intent intentRecibido){

        clsJugadorFutbol jf = (clsJugadorFutbol) intentRecibido.getParcelableExtra("futbolista");
        ArrayList<String> posiciones = jf.getPosiciones();
        posiciones.add(etxNuevaPosicion.getText().toString());
        jf.setPosiciones(posiciones);
        adapSpinner.notifyDataSetChanged();
        txvNuevaPosicion.setVisibility(View.INVISIBLE);
        etxNuevaPosicion.setVisibility(View.INVISIBLE);
        if(!btnGuardarNuevaPosicion.isEnabled()) {
            btnGuardarNuevaPosicion.setEnabled(btnGuardarNuevaPosicion.isEnabled());
        }
    }

    public void switchActivarCampos(){

        Nombre.setEnabled(!Nombre.isEnabled());
        Foto.setEnabled(!Foto.isEnabled());
        spinner.setEnabled(!spinner.isEnabled());
        btnNuevaPosicion.setEnabled(!btnNuevaPosicion.isEnabled());
        txvNuevaPosicion.setVisibility(View.INVISIBLE);
        etxNuevaPosicion.setVisibility(View.INVISIBLE);
        if(btnGuardarNuevaPosicion.isEnabled()) {
            btnGuardarNuevaPosicion.setEnabled(!btnGuardarNuevaPosicion.isEnabled());
        }
    }

    public void mostrarIntentRecibido(Intent intentRecibido){

        clsJugadorFutbol jf = (clsJugadorFutbol) intentRecibido.getParcelableExtra("futbolista");
        Nombre.setText(jf.getNombre());
        Foto.setImageResource(jf.getFoto());
        adapSpinner= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,jf.getPosiciones());
        adapSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapSpinner);
    }
}
