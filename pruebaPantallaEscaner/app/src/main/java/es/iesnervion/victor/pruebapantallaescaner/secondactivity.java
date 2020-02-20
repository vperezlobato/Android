package es.iesnervion.victor.pruebapantallaescaner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;

public class secondactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }
}
