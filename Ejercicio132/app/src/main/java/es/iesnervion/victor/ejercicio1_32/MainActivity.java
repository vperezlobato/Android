package es.iesnervion.victor.ejercicio1_32;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
//import android.widget.CheckBox;

//import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {
    private CheckBox negrita,grande,pequena,rojo;
    private TextView texto;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        texto =  findViewById(R.id.texto);
        negrita =  findViewById(R.id.negrita);
        rojo =  findViewById(R.id.rojo);
        pequena =  findViewById(R.id.pequena);
        grande =  findViewById(R.id.grande);
    }

    public void aplicarCambios(View v){
                if(negrita.isChecked()) {
                    texto.setTypeface(texto.getTypeface(),Typeface.BOLD);
                }else
                    texto.setTypeface(null,Typeface.NORMAL);

                if(rojo.isChecked()) {
                    texto.setTextColor(Color.RED);
                }else
                    texto.setTextColor(Color.BLACK);

                if(grande.isChecked()){
                    texto.setTextSize(50);
                }else
                    texto.setTextSize(15);

                if(pequena.isChecked()){
                    texto.setTextSize(10);
                }
    }


}
