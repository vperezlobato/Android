package com.example.buscaminas;

import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.example.buscaminas.Clases.clsCasilla;
import com.example.buscaminas.Clases.tablero;
import com.example.buscaminas.ViewModelJuego.ViewModelJuego;

public class ManejadorJuego {

    //Este metodo se encarga de establecer la minas en el tablero de forma aleatoria
    public void establecerMinas(int numeroMinas, int altura, int ancho, tablero tablero) {
        int alturaAleatoria = 0;
        int anchoAleatoria = 0;
        int cantidad = numeroMinas;
        for(int i= cantidad; cantidad != 0; cantidad-- ){
            alturaAleatoria = (int) (Math.random() * (altura-1));
            anchoAleatoria = (int) (Math.random() * (ancho-1));
            if (tablero.getTablero()[alturaAleatoria][anchoAleatoria].getEsBomba() == false) {
                tablero.getTablero()[alturaAleatoria][anchoAleatoria].setEsBomba(true);
            }else
                cantidad++;
        }
    }

    //Este metodos se encarga de guardar en una casilla un numero que dependera de las bombas que haya a su alrededor
    public void ponerNumerosEnLasCasillas (int altura, int ancho, tablero tablero) {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < ancho; j++) {
                if (tablero.getTablero()[i][j].getEsBomba() == false) {
                    int cantidad = bombasAlrededorDeLaCasilla(i, j,tablero,ancho,altura);
                    tablero.getTablero()[i][j].setNumero(cantidad);
                }
            }
        }
    }

    //Este metodo se encarga de comprobar si hay una bomba en las casillas alrededor
    //de las coordenadas pasadas y devuelve un numero en funcion de si encuentra alguna o no
    public int bombasAlrededorDeLaCasilla(int fila, int columna,tablero tablero, int ancho, int altura) {
        int total = 0;
        if (fila - 1 >= 0 && columna - 1 >= 0) {
            if (tablero.getTablero()[fila - 1][columna - 1].getEsBomba())
                total++;
        }
        if (fila - 1 >= 0) {
            if (tablero.getTablero()[fila - 1][columna].getEsBomba())
                total++;
        }
        if (fila - 1 >= 0 && columna + 1 < ancho) {
            if (tablero.getTablero()[fila - 1][columna + 1].getEsBomba())
                total++;
        }
        if (columna + 1 < ancho) {
            if (tablero.getTablero()[fila][columna + 1].getEsBomba())
                total++;
        }
        if (fila + 1 < altura && columna + 1 < ancho) {
            if (tablero.getTablero()[fila + 1][columna + 1].getEsBomba())
                total++;
        }
        if (fila + 1 < altura) {
            if (tablero.getTablero()[fila + 1][columna].getEsBomba())
                total++;
        }
        if (fila + 1 < altura && columna - 1 >= 0) {
            if (tablero.getTablero()[fila + 1][columna - 1].getEsBomba())
                total++;
        }
        if (columna - 1 >= 0) {
            if (tablero.getTablero()[fila][columna - 1].getEsBomba())
                total++;
        }
        return total;
    }

    //Este metodo se encarga de inicializar el cronometro de la partida
    public void empezarCronometro(ViewModelJuego vm, Chronometer cronometro){
        if(vm.isPrimerClick()){
            cronometro.setBase(SystemClock.elapsedRealtime());
            cronometro.start();
            vm.setPrimerClick(false);
        }
    }


    //Metodo para obtener una casilla en concreto en funcion del imageView que haya sido pulsado
    public clsCasilla obtenerCasilla(tablero tablero, int id){
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

    //Este metodo se encarga de ponerle una imagen con numero a la casilla en funcion del numero de bombas que se encontraran a su alrededor
    public void ponerleImagenDeNumeroAlaCasilla(ImageView casillaSeleccionada, clsCasilla casilla){

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

}
