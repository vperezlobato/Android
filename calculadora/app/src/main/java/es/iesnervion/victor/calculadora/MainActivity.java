package es.iesnervion.victor.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView texto,resultado;
    String guardarTexto = "",operador = "",mostrar ="";
    double igual,txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.texto);
        resultado = findViewById(R.id.resultado);
    }
    public void numeroCero(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"0" ;
        texto.setText(mostrar);
    }
    public void numeroUno(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"1" ;
        texto.setText(mostrar);
    }

    public void numeroDos(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"2" ;
        texto.setText(mostrar);
    }

    public void numeroTres(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"3";
        texto.setText(mostrar);
    }
    public void numeroCuatro(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"4";
        texto.setText(mostrar);
    }
    public void numeroCinco(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"5" ;
        texto.setText(mostrar);
    }
    public void numeroSeis(View v){
        mostrar = texto.getText().toString();
        mostrar = "6" + mostrar;
        texto.setText(mostrar);
    }

    public void numeroSiete(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"7" ;
        texto.setText(mostrar);
    }

    public void numeroOcho(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"8" ;
        texto.setText(mostrar);
    }

    public void numeroNueve(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"9" ;
        texto.setText(mostrar);
    }

    public void multiplicar(View v){
        guardarTexto = texto.getText().toString();
        operador = "x";
        texto.setText("");
    }

    public void dividir(View v){
        guardarTexto = texto.getText().toString();
        operador = "÷";
        texto.setText("");
    }

    public void suma(View v){
        guardarTexto = texto.getText().toString();
        operador = "+";
        texto.setText("");
    }

    public void menos(View v){
        guardarTexto = texto.getText().toString();
        operador = "-";
        texto.setText("");
    }

    public void punto(View v){
        mostrar = texto.getText().toString();
        mostrar = mostrar +"." ;
        texto.setText(mostrar);
    }

    public void limpiar(View v){
        guardarTexto = resultado.getText().toString();
        guardarTexto = "";
        resultado.setText("");
        operador = "";
    }

    public void borrar(View v){
        mostrar = texto.getText().toString();
        if(mostrar.length() > 0) {
            mostrar = mostrar.substring(0, mostrar.length() - 1);
        }
        texto.setText(mostrar);
    }

    public void igual(View v){
        if(operador.equals("+") && guardarTexto != "" && texto.getText() != ""){
            igual = Double.parseDouble(guardarTexto) + Double.parseDouble(texto.getText().toString());
            resultado.setText("=" + igual);
            texto.setText("");
        }

        if(operador.equals("-") && guardarTexto != "" && texto.getText() != ""){
            igual = Double.parseDouble(guardarTexto) - Double.parseDouble(texto.getText().toString());
            resultado.setText("=" + igual);
            texto.setText("");
        }

        if(operador.equals("x") && guardarTexto != "" && texto.getText() != ""){
            igual = Double.parseDouble(guardarTexto) * Double.parseDouble(texto.getText().toString());
            resultado.setText("=" + igual);
            texto.setText("");
        }

        if(operador.equals("÷") && guardarTexto != "" && texto.getText() != ""){
            igual = Double.parseDouble(guardarTexto) / Double.parseDouble(texto.getText().toString());
            resultado.setText("=" + igual);
            texto.setText("");
        }
    }
}
