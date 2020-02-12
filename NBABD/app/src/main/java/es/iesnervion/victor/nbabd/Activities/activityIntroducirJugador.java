package es.iesnervion.victor.nbabd.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.iesnervion.victor.nbabd.Clases.Jugador;
import es.iesnervion.victor.nbabd.R;
import es.iesnervion.victor.nbabd.Room.usarDatabase;

public class activityIntroducirJugador extends AppCompatActivity implements View.OnClickListener{

    private TextView nombreJugador,apellidosJugador;
    private EditText nombre,apellidos;
    private Button boton;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducir_jugador);

        nombreJugador = findViewById(R.id.textViewNombreJugador);
        apellidosJugador = findViewById(R.id.Apellido);
        nombre = findViewById(R.id.textViewNombreJugadorEscrito);
        apellidos = findViewById(R.id.ApellidoEscrito);
        boton = findViewById(R.id.botonInsertarJugador);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        id = bundle.getInt("IDEquipo");

        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        String nombreJ = nombre.getText().toString();
        String apellidosJ = apellidos.getText().toString();
        Jugador jugador = new Jugador(nombreJ,apellidosJ,id);

        usarDatabase.getDatabase(this).dao().insertarJugador(jugador);
        recreate();
    }
}

