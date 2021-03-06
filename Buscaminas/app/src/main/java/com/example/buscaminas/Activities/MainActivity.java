package com.example.buscaminas.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.buscaminas.R;
import com.example.buscaminas.Adapters.ViewPagerAdapter;
import com.example.buscaminas.Animacion_ViewPager.ZoomOutPageTransformer;
import com.example.buscaminas.Play.PlayFragment;
import com.example.buscaminas.ui.Clasificacion.ClasificacionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView ;
    private ViewPager viewPager;
    private PlayFragment playFragment;
    private ClasificacionFragment clasificacionFragment;
    private MediaPlayer mediaPlayer;
    private boolean desactivarSonido;
    private Menu menu;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView  = findViewById(R.id.nav_view);

        //para controlar cada vez que desactivas y activas el sonido entre el cierre de la aplicacion y la navegacion entre los activities utilizo un sharedPreference
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyDesactivarSonidoPref",MODE_PRIVATE);
        editor = prefs.edit();
        desactivarSonido = prefs.getBoolean("sonidoDesactivar",false);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true,new ZoomOutPageTransformer()); //Animacion para cuando deslizas entre paginas,
                                                                                           // cuanto mas cerca esta del desliz a derecha o izquierda se ve que se pone al fondo
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true); //Al hacer un deslizamiento de pag se le dice al mismo tiempo al bottomNavigationView su nueva posicion
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        setupViewPager(viewPager);

        if (!desactivarSonido) { //Si no esta desactivado el sonido empieza a sonar
            mediaPlayer = MediaPlayer.create(this, R.raw.suspenso);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean pagActual = false;

        switch (item.getItemId()) {

            case R.id.navigation_Play:
                viewPager.setCurrentItem(0); //Lo mismo que le dices al bottomNavigationView cuando deslizas el viewpager, hay que hacer pero esta vez al contrario,
                                             // pinchas el bottomNavigationView y le dices al viewpager su nueva posicion seleccionada
                pagActual = true;
            break;

            case R.id.navigation_Clasificacion:
                viewPager.setCurrentItem(1);
                pagActual = true;
            break;
        }
        return pagActual;
    }

    //En este metodo se carga el listado de fragmentos del viewpager que son 2
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        playFragment =new PlayFragment();
        clasificacionFragment = new ClasificacionFragment();
        adapter.addFragment(playFragment);
        adapter.addFragment(clasificacionFragment);
        viewPager.setAdapter(adapter);
    }

    //Este es el menu superior a la derecha para cerrar sesion y desactivar el sonido
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu,menu);
        MenuItem item = menu.findItem(R.id.desactivarSonido);

        if(!desactivarSonido){
            item.setTitle("Desactivar sonido");

        }else
            item.setTitle("Activar sonido");

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean itemSeleccionado = false;
        switch(item.getItemId()){
            case R.id.cerrarSesion: //Si pulsa el boton se cierra sesion en firebase y te envia denuevo al login
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                overridePendingTransition(R.transition.fade_out, R.transition.fade_out);
                itemSeleccionado = true;
            break;
            case R.id.desactivarSonido:
                if(item.getTitle().equals("Desactivar sonido")){
                    mediaPlayer.pause();
                    item.setTitle("Activar sonido");
                    editor.putBoolean("sonidoDesactivar",true);
                }else{
                    mediaPlayer = MediaPlayer.create(this, R.raw.suspenso);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    item.setTitle("Desactivar sonido");
                    editor.putBoolean("sonidoDesactivar",false);
                }
            break;

        }
        editor.commit();
        return itemSeleccionado;
    }

    @Override
    public void onPause(){
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyDesactivarSonidoPref",MODE_PRIVATE);
        desactivarSonido = prefs.getBoolean("sonidoDesactivar",false);
        if(menu != null){
            MenuItem item = menu.findItem(R.id.desactivarSonido);
            if (!desactivarSonido) {
                item.setTitle("Desactivar sonido");
                mediaPlayer = MediaPlayer.create(this, R.raw.suspenso);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            } else
                item.setTitle("Activar sonido");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //Esto es para que cuando cambies al fragmento de la clasificacion si le das atras vuelva al fragmento de play y no se salga de la app
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
