package es.iesnervion.victor.calculadoraradiobuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Double texto = 0.0;
    EditText numero1, numero2;
    TextView resultado;
    RadioButton sumar, restar, multiplicar, division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numero1 = findViewById(R.id.editNumero1);
        numero2 = findViewById(R.id.editNumero2);
        sumar = findViewById(R.id.sumar);
        restar = findViewById(R.id.resta);
        multiplicar = findViewById(R.id.multiplicar);
        division = findViewById(R.id.dividir);
        resultado = findViewById(R.id.resultado);

    }

    public void aplicar(View view) {
        if (sumar.isChecked()){
            texto = Double.parseDouble(numero1.getText().toString()) + Double.parseDouble(numero2.getText().toString());
            resultado.setText(texto.toString());
        }else
            if (restar.isChecked()){
                texto = Double.parseDouble(numero1.getText().toString()) - Double.parseDouble(numero2.getText().toString());
                resultado.setText(texto.toString());
            }else
                if (multiplicar.isChecked()){
                    texto = Double.parseDouble(numero1.getText().toString()) * Double.parseDouble(numero2.getText().toString());
                    resultado.setText(texto.toString());
                }else {
                    texto = Double.parseDouble(numero1.getText().toString()) / Double.parseDouble(numero2.getText().toString());
                    resultado.setText(texto.toString());
                }
    }
}
