package com.example.buscaminas.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buscaminas.Clases.Partida;
import com.example.buscaminas.ManejadorJuego;
import com.example.buscaminas.R;
import com.example.buscaminas.ViewModelJuego.ViewModelJuego;
import com.example.buscaminas.Clases.clsCasilla;
import com.example.buscaminas.Clases.tablero;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JuegoActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    private ViewModelJuego vm;
    private String dificultad;
    private ConstraintLayout layout;
    private int idArray[][];
    private Chronometer cronometro;
    private TextView minas;
    private ImageView carita;
    private int minasParaReinicio;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private MediaPlayer mediaPlayer;
    private ManejadorJuego mj;
    private boolean desactivarSonido;
    private SharedPreferences.Editor editor;
    private Handler handler;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        hideSystemUI();
        handler = new Handler();
        decorView = getWindow().getDecorView();

        mj = new ManejadorJuego();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyDesactivarSonidoPref",MODE_PRIVATE);
        editor = prefs.edit();
        desactivarSonido = prefs.getBoolean("sonidoDesactivar",false);

        cronometro = findViewById(R.id.cronometro);
        minas = findViewById(R.id.numeroMinas);
        carita = findViewById(R.id.carita);
        layout = findViewById(R.id.layout);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
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
                vm.setNumeroMinas(10);
                break;
            case "Nivel Dificil":
                vm.setAltura(16);
                vm.setAncho(16);
                vm.setNumeroMinas(40);
                break;

            case "Nivel Extremo":
                vm.setAltura(16);
                vm.setAncho(16);
                vm.setNumeroMinas(60);
                break;

        }

        minas.setText(String.valueOf(vm.getNumeroMinas()));
        minasParaReinicio = vm.getNumeroMinas();
        vm.crearPartida();

        pintarTablero(); // 0 - for private mode

        carita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.setNumeroMinas(minasParaReinicio);
                //Para optimizar el funcionamiento de la partida necesitamos limpiar el tablero porque si no cada vez que genera un nuevo tablero
                // realizaria la misma funcion segun el numero de tableros que he generado.
                // Por ejemplo si he reiniciado la partida 3 veces estaria haciendo todos los procesos de busqueda 3 veces
                ConstraintSet cs = new ConstraintSet();
                cs.clear(R.id.gridFrame);
                cs.applyTo(layout);
                for (int iRow = 0; iRow < vm.getTablero().getAltura(); iRow++) {
                    for (int iCol = 0; iCol < vm.getAncho(); iCol++) {
                          View view =  layout.getViewById(idArray[iRow][iCol]);
                          layout.removeView(view);
                    }
                }
                vm.crearPartida();
                if(mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                pintarTablero();
                minas.setText(String.valueOf(vm.getNumeroMinas()));
                carita.setImageResource(R.drawable.caritasonriente);
                vm.setPrimerClick(true);
                vm.setJugando(true);
                cronometro.stop();
                cronometro.setBase(SystemClock.elapsedRealtime());
            }
        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0){
                    handler.postDelayed(autoHideRunner,3000);
                }
            }
        });
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
        clsCasilla casilla = mj.obtenerCasilla(vm.getTablero(),v.getId());
        mj.empezarCronometro(vm,cronometro);

        if(vm.isJugando()){
            if(!casilla.getBanderaPuesta()) {
                if (!casilla.getYaPulsada()) {
                    if (casilla.getEsBomba()) {
                        if(!desactivarSonido) {
                            mediaPlayer = MediaPlayer.create(this, R.raw.explosion);
                            mediaPlayer.start();
                        }
                        perder(casilla);
                    } else {
                        if(casilla.getNumero() == 0){
                            mostrarCasillasAlrededor(casilla.getPosX(),casilla.getPosY());
                            if(!desactivarSonido) {
                                mediaPlayer = MediaPlayer.create(this, R.raw.limpiarvariascasilla);
                                mediaPlayer.start();
                            }
                            victoria();
                        }else {
                            mj.ponerleImagenDeNumeroAlaCasilla(casillaSeleccionada, casilla);
                            casilla.setYaPulsada(true);
                            if(!desactivarSonido) {
                                mediaPlayer = MediaPlayer.create(this, R.raw.limpiarunacasilla);
                                mediaPlayer.start();
                            }
                            victoria();
                        }

                    }
                }
            }
        }
    }

    private void perder(clsCasilla casilla) {
        vm.setJugando(false);
        mostrarMinas(casilla.getPosX(), casilla.getPosY());
        cronometro.stop();
        carita.setImageResource(R.drawable.caritaperdedor);
        final String id = user.getUid();
        final boolean[] primeraInserccion = {true}; //esto es un poco raro pero es la unica forma que he encontrado de controlar desde fuera del metodo asyncrono los if´s de dentro
        mDatabase.child("Usuarios").child(id).child(dificultad).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Partida partida = dataSnapshot.getValue(Partida.class);

                if (partida == null && primeraInserccion[0]) {
                    partida = new Partida();
                    partida.setDificultad(dificultad);
                    partida.setNumeroPartidas(1);
                    primeraInserccion[0] = false;
                } else if (partida != null && primeraInserccion[0]) {
                    partida.setNumeroPartidas(partida.getNumeroPartidas() + 1);
                    primeraInserccion[0] = false;
                }

                mDatabase.child("Usuarios").child(id).child(partida.getDificultad()).setValue(partida);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "Has perdido", Toast.LENGTH_SHORT).show();
    }

    private void victoria() {
        final String id = user.getUid();
        final boolean[] primeraInserccion = {true};

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
            if(!desactivarSonido) {
                if (dificultad.equals("Nivel Extremo")) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.triunfoextremo);
                    mediaPlayer.start();
                } else {
                    mediaPlayer = MediaPlayer.create(this, R.raw.ganar);
                    mediaPlayer.start();
                }
            }

            cronometro.stop();
            vm.setJugando(false);
            //-----------------------------------------------------------------
             //esto es un poco raro pero es la unica forma que he encontrado de controlar desde fuera del metodo asyncrono lso if´s de dentro
            mDatabase.child("Usuarios").child(id).child(dificultad).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Partida partida = dataSnapshot.getValue(Partida.class);

                    if(partida == null && primeraInserccion[0]){
                        partida = new Partida();
                        partida.setDificultad(dificultad);
                        partida.setNumeroPartidas(1);
                        partida.setNumeroPartidasGanadas(1);
                        primeraInserccion[0] = false;
                    }else
                        if(partida != null && primeraInserccion[0]){
                        partida.setNumeroPartidas(partida.getNumeroPartidas()+1);
                        partida.setNumeroPartidasGanadas(partida.getNumeroPartidasGanadas()+1);
                        primeraInserccion[0] = false;
                        }

                    mDatabase.child("Usuarios").child(id).child(partida.getDificultad()).setValue(partida);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //------------------------------------------------------------------
        }
    }

    //Este metodo es recursivo y se encarga de descubrir las casillas en funcion de la casilla pulsada
    //por lo que si pulsamos una casilla sin numero mostrara a su alrededor las que no sean bomba y
    //si encontrara una bomba dejaria de seguir mirando a la siguiente casilla y mostraria el numero de esta
    private void mostrarCasillasAlrededor(int posX, int posY) {
        ImageView imagen;
        if (posX >= 0 && posX < vm.getAltura() && posY >= 0 && posY < vm.getAncho() && !vm.getTablero().getTablero()[posX][posY].getBanderaPuesta()) {
            if (vm.getTablero().getTablero()[posX][posY].getNumero() == 0) {
                imagen = findViewById(vm.getTablero().getTablero()[posX][posY].getId());
                mj.ponerleImagenDeNumeroAlaCasilla(imagen, vm.getTablero().getTablero()[posX][posY]);
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
                mj.ponerleImagenDeNumeroAlaCasilla(imagen, vm.getTablero().getTablero()[posX][posY]);
                vm.getTablero().getTablero()[posX][posY].setYaPulsada(true);
            }
        }
    }

    //Este metodo muestra todas las minas del tablero una vez se haya perdido
    private void mostrarMinas(int posX,int posY) {
        ImageView imagen;

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

    //Con este longclick se pondran y quitaran banderas
    @Override
    public boolean onLongClick(View v) {
        ImageView casillaSeleccionada = (ImageView)v;
        boolean pulsoLargo = false;
        clsCasilla casilla = mj.obtenerCasilla(vm.getTablero(),v.getId());
        if(vm.isJugando()){
            mj.empezarCronometro(vm,cronometro);
            if(!casilla.getBanderaPuesta()) {
                if (!casilla.getYaPulsada()) {
                    casillaSeleccionada.setImageResource(R.drawable.bandera);
                    if(!desactivarSonido) {
                        mediaPlayer = MediaPlayer.create(this, R.raw.banderapuesta);
                        mediaPlayer.start();
                    }
                    casilla.setBanderaPuesta(true);
                    casilla.setYaPulsada(true);
                    vm.setNumeroMinas(vm.getNumeroMinas()-1);
                    minas.setText(String.valueOf(vm.getNumeroMinas()));
                    pulsoLargo = true;
                }
            }else{
                casillaSeleccionada.setImageResource(R.drawable.casilla);
                if(!desactivarSonido) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.banderaquitada);
                    mediaPlayer.start();
                }
                casilla.setBanderaPuesta(false);
                casilla.setYaPulsada(false);
                vm.setNumeroMinas(vm.getNumeroMinas()+1);
                minas.setText(String.valueOf(vm.getNumeroMinas()));
                pulsoLargo = true;
            }
        }
        return pulsoLargo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu,menu);
        MenuItem item = menu.findItem(R.id.desactivarSonido);

        if(!desactivarSonido){
            item.setTitle("Desactivar sonido");

        }else
            item.setTitle("Activar sonido");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean itemSeleccionado = false;
        switch(item.getItemId()){
            case R.id.cerrarSesion:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(JuegoActivity.this,LoginActivity.class));
                overridePendingTransition(R.transition.fade_out, R.transition.fade_out);
                itemSeleccionado = true;
            break;
            case R.id.desactivarSonido:
                if(item.getTitle().equals("Desactivar sonido")){

                    mediaPlayer.stop();
                    desactivarSonido = true;
                    item.setTitle("Activar sonido");
                    editor.putBoolean("sonidoDesactivar",true);
                }else{
                    desactivarSonido = false;
                    item.setTitle("Desactivar sonido");
                    editor.putBoolean("sonidoDesactivar",false);
                }
                itemSeleccionado = true;
            break;

        }
        editor.commit();
        return itemSeleccionado;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    private final Runnable autoHideRunner = new Runnable() {
        @Override
        public void run() {
            hideSystemUI();
        }
    };

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.transition.fade_out, R.transition.fade_out);
    }

}
