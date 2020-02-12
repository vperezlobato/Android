package es.iesnervion.victor.nbabd.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.iesnervion.victor.nbabd.Clases.Equipo;
import es.iesnervion.victor.nbabd.R;
import es.iesnervion.victor.nbabd.Room.usarDatabase;

public class activity_introducir_equipo extends AppCompatActivity implements View.OnClickListener{

    private TextView nombre,nombreSimple,abreviacion,localizacion;
    private EditText nombreEscrito,nombreSimpleEscrito,abreviacionEscrita,localizacionEscrita;
    private Button insertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducir_equipo);

        nombre = findViewById(R.id.introducirNombreEquipo);
        nombreSimple = findViewById(R.id.introducirNombreSimple);
        abreviacion = findViewById(R.id.introducirAbreviacion);
        localizacion = findViewById(R.id.introducirLocalizacion);
        nombreEscrito = findViewById(R.id.introducirNombreEquipoEscrito);
        nombreSimpleEscrito = findViewById(R.id.introducirNombreSimpleEscrito);
        abreviacionEscrita = findViewById(R.id.introducirAbreviacionEscrita);
        localizacionEscrita = findViewById(R.id.introducirLocalizacionEscrita);
        insertar = findViewById(R.id.botonInsertarEquipo);

        insertar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String nombre = nombreEscrito.getText().toString();
        String nombreSimple = nombreSimpleEscrito.getText().toString();
        String abreviacion = abreviacionEscrita.getText().toString();
        String localizacion = localizacionEscrita.getText().toString();
        Equipo equipo = new Equipo(abreviacion,nombre,nombreSimple,localizacion);

        usarDatabase.getDatabase(this).dao().insertarEquipo(equipo);
        recreate();
    }
}
