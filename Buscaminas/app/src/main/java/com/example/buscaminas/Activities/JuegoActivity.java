package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buscaminas.R;
import com.example.buscaminas.ViewModelJuego;
import com.example.buscaminas.clsCasilla;
import com.example.buscaminas.tablero;

import java.io.File;

public class JuegoActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    private ViewModelJuego vm;
    private String dificultad;
    private ConstraintLayout layout;
    private int idArray[][];
    private Chronometer cronometro;
    private TextView minas;
    private ImageView carita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        cronometro = findViewById(R.id.cronometro);
        minas = findViewById(R.id.numeroMinas);
        carita = findViewById(R.id.carita);
        layout = findViewById(R.id.layout);

        vm = ViewModelProviders.of(this).get(ViewModelJuego.class);
        Intent intent = getIntent();
        dificultad = intent.getStringExtra("dificultad");

        switch (dificultad) {
            case "Nivel Facil":
                vm.setAltura(6);
                vm.setAncho(6);
                vm.setNumeroMinas(4);
                break;
            case "Nivel Medio":
                vm.setAltura(8);
                vm.setAncho(8);
                vm.setNumeroMinas(15);
                break;
            case "Nivel Dificil":
                vm.setAltura(16);
                vm.setAncho(16);
                vm.setNumeroMinas(40);
                break;

            case "Nivel Extremo":
                vm.setAltura(14);
                vm.setAncho(14);
                vm.setNumeroMinas(100);
                break;

        }

        minas.setText(String.valueOf(vm.getNumeroMinas()));

        vm.crearPartida();

        pintarTablero();

    }



    public void pintarTablero(){


        ImageView imageView;
        ConstraintLayout.LayoutParams lp;
        int id = 0;
        idArray = new int[vm.getAltura()][vm.getAncho()];
        ConstraintSet cs = new ConstraintSet();

        // Add our views to the ConstraintLayout.
        for (int iRow = 0; iRow < vm.getTablero().getAltura(); iRow++) {
            for (int iCol = 0; iCol < vm.getAncho(); iCol++) {
                imageView = new ImageView(this);

                lp = new ConstraintLayout.LayoutParams(ConstraintSet.MATCH_CONSTRAINT,
                        ConstraintSet.MATCH_CONSTRAINT);
                id = View.generateViewId();
                vm.getTablero().getTablero()[iRow][iCol].setId(id);
                idArray[iRow][iCol] = id;
                imageView.setId(id);
                imageView.setImageResource(vm.getTablero().getTablero()[iRow][iCol].getImagen());
                imageView.setOnClickListener(this);
                imageView.setOnLongClickListener(this);
                layout.addView(imageView, lp);
            }
        }

        // Create horizontal chain for each row and set the 1:1 dimensions.
        // but first make sure the layout frame has the right ratio set.
        cs.clone(layout);
        cs.setDimensionRatio(R.id.gridFrame, vm.getAltura() + ":" + vm.getAncho());
        for (int iRow = 0; iRow < vm.getAltura(); iRow++) {
            for (int iCol = 0; iCol < vm.getAncho(); iCol++) {
                id = vm.getTablero().getTablero()[iRow][iCol].getId();
                cs.setDimensionRatio(id, "1:1");
                if (iRow == 0) {
                    // Connect the top row to the top of the frame.
                    cs.connect(id, ConstraintSet.TOP, R.id.gridFrame, ConstraintSet.TOP);
                } else {
                    // Connect top to bottom of row above.
                    cs.connect(id, ConstraintSet.TOP, vm.getTablero().getTablero()[iRow - 1][0].getId(), ConstraintSet.BOTTOM);
                }
            }
            // Create a horiontal chain that will determine the dimensions of our squares.
            // Could also be createHorizontalChainRtl() with START/END.
            cs.createHorizontalChain(R.id.gridFrame, ConstraintSet.LEFT,
                    R.id.gridFrame, ConstraintSet.RIGHT,
                    idArray[iRow], null, ConstraintSet.CHAIN_PACKED);
        }

        cs.applyTo(layout);
    }

    @Override
    public void onClick(View v) {
        ImageView casillaSeleccionada = (ImageView)v;
        clsCasilla casilla = obtenerCasilla(vm.getTablero(),v.getId());
        empezarCronometro();

        if(vm.isJugando()){
            if(!casilla.getBanderaPuesta()) {
                if (!casilla.getYaPulsada()) {
                    if (casilla.getEsBomba()) {
                        vm.setJugando(false);
                        mostrarMinas(casilla.getPosX(),casilla.getPosY());
                        cronometro.stop();
                        carita.setImageResource(R.drawable.caritaperdedor);
                        Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show();
                    } else {
                        if(casilla.getNumero() == 0){
                            //TODO metodo recursivo para descubrir las casillas
                            mostrarCasillasAlrededor(casilla.getPosX(),casilla.getPosY());
                            victoria();
                        }else {
                            ponerleImagenDeNumeroAlaCasilla(casillaSeleccionada, casilla);
                            casilla.setYaPulsada(true);
                            victoria();
                        }

                    }
                }
            }
        }
    }

    private void victoria() {
        int numeroDeCasillaSinMinas = (vm.getAltura()*vm.getAncho()) - vm.getNumeroMinas();
        int contadorCasillasMostradas = 0;
        for(int i = 0; i < vm.getAltura(); i++){
            for(int j = 0; j < vm.getAncho(); j++){
                if(vm.getTablero().getTablero()[i][j].getYaPulsada()){
                    contadorCasillasMostradas++;
                }

            }
        }
        if(numeroDeCasillaSinMinas == contadorCasillasMostradas) {
            carita.setImageResource(R.drawable.caritaganador);
            Toast.makeText(this, "Has ganado!", Toast.LENGTH_SHORT).show();
            cronometro.stop();
            vm.setJugando(false);
        }
    }

    private void mostrarCasillasAlrededor(int posX, int posY) {
        ImageView imagen;
        if (posX >= 0 && posX < vm.getAltura() && posY >= 0 && posY < vm.getAncho() && !vm.getTablero().getTablero()[posX][posY].getBanderaPuesta() && !vm.getTablero().getTablero()[posX][posY].getBanderaPuesta()) {
            if (vm.getTablero().getTablero()[posX][posY].getNumero() == 0) {
                imagen = findViewById(vm.getTablero().getTablero()[posX][posY].getId());
                ponerleImagenDeNumeroAlaCasilla(imagen, vm.getTablero().getTablero()[posX][posY]);
                vm.getTablero().getTablero()[posX][posY].setYaPulsada(true);
                vm.getTablero().getTablero()[posX][posY].setNumero(50);
                mostrarCasillasAlrededor(posX, posY + 1);
                mostrarCasillasAlrededor(posX, posY - 1);
                mostrarCasillasAlrededor(posX + 1, posY);
                mostrarCasillasAlrededor(posX - 1, posY);
                mostrarCasillasAlrededor(posX - 1, posY - 1);
                mostrarCasillasAlrededor(posX - 1, posY + 1);
                mostrarCasillasAlrededor(posX + 1, posY + 1);
                mostrarCasillasAlrededor(posX + 1, posY - 1);

            } else if (vm.getTablero().getTablero()[posX][posY].getNumero() >= 1 && !vm.getTablero().getTablero()[posX][posY].getEsBomba() && !vm.getTablero().getTablero()[posX][posY].getBanderaPuesta()) {
                imagen = findViewById(vm.getTablero().getTablero()[posX][posY].getId());
                ponerleImagenDeNumeroAlaCasilla(imagen, vm.getTablero().getTablero()[posX][posY]);
                vm.getTablero().getTablero()[posX][posY].setYaPulsada(true);
            }
        }
    }

    private void mostrarMinas(int posX,int posY) {
        ImageView imagen;
        int contador = 0;
        for(int i = 0; i < vm.getAltura();i++){
            for(int j = 0; j< vm.getAncho();j++){
                if(vm.getTablero().getTablero()[i][j].getEsBomba() && vm.getTablero().getTablero()[i][j].getBanderaPuesta()) {
                    imagen = findViewById(idArray[i][j]);
                    imagen.setImageResource(R.drawable.bombatachada);
                }else
                    if(vm.getTablero().getTablero()[i][j].getEsBomba()) {
                        imagen = findViewById(idArray[i][j]);
                        if(i == posX && j == posY){
                            imagen.setImageResource(R.drawable.bombatocada);
                        }else
                            imagen.setImageResource(R.drawable.mina);
                    }

            }

        }
    }

    public void ponerleImagenDeNumeroAlaCasilla(ImageView casillaSeleccionada,clsCasilla casilla){

        switch (casilla.getNumero()) {
            case 0:
                casillaSeleccionada.setImageResource(R.drawable.nobomba);
                break;
            case 1:
                casillaSeleccionada.setImageResource(R.drawable.numero1);
                break;
            case 2:
                casillaSeleccionada.setImageResource(R.drawable.numero2);
                break;
            case 3:
                casillaSeleccionada.setImageResource(R.drawable.numero3);
                break;
            case 4:
                casillaSeleccionada.setImageResource(R.drawable.numero4);
                break;
            case 5:
                casillaSeleccionada.setImageResource(R.drawable.numero5);
                break;
            case 6:
                casillaSeleccionada.setImageResource(R.drawable.numero6);
                break;
            case 7:
                casillaSeleccionada.setImageResource(R.drawable.numero7);
                break;
            case 8:
                casillaSeleccionada.setImageResource(R.drawable.numero8);
                break;

        }
    }

    public clsCasilla obtenerCasilla(tablero tablero,int id){
        clsCasilla casilla = new clsCasilla();

        for(int i = 0;i < tablero.getAltura(); i++){
            for(int j = 0; j < tablero.getAncho(); j++){
                if(tablero.getTablero()[i][j].getId() == id){
                    casilla = tablero.getTablero()[i][j];

                }
            }
        }
        return casilla;
    }

    @Override
    public boolean onLongClick(View v) {
        ImageView casillaSeleccionada = (ImageView)v;
        boolean pulsoLargo = false;
        clsCasilla casilla = obtenerCasilla(vm.getTablero(),v.getId());

        empezarCronometro();

        if(!casilla.getBanderaPuesta()) {
            if (!casilla.getYaPulsada()) {
                casillaSeleccionada.setImageResource(R.drawable.bandera);
                casilla.setBanderaPuesta(true);
                casilla.setYaPulsada(true);
                vm.setNumeroMinas(vm.getNumeroMinas()-1);
                minas.setText(String.valueOf(vm.getNumeroMinas()));
                pulsoLargo = true;
            }
        }else{
            casillaSeleccionada.setImageResource(R.drawable.casilla);
            casilla.setBanderaPuesta(false);
            casilla.setYaPulsada(false);
            vm.setNumeroMinas(vm.getNumeroMinas()+1);
            minas.setText(String.valueOf(vm.getNumeroMinas()));
            pulsoLargo = true;
        }
        return pulsoLargo;
    }

    public void empezarCronometro(){
        if(vm.isPrimerClick()){
            cronometro.setBase(SystemClock.elapsedRealtime());
            cronometro.start();
            vm.setPrimerClick(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu,menu);
        return true;
    }

}
