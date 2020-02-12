package es.iesnervion.victor.nbabd.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import es.iesnervion.victor.nbabd.Adapter.AdaptadorDeEquipos;
import es.iesnervion.victor.nbabd.Adapter.FragmentJugadores;
import es.iesnervion.victor.nbabd.Clases.Equipo;
import es.iesnervion.victor.nbabd.Clases.Jugador;
import es.iesnervion.victor.nbabd.R;
import es.iesnervion.victor.nbabd.Room.usarDatabase;
import es.iesnervion.victor.nbabd.ViewModels.ActivityJugadoresVM;

public class datosEquipoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView nombreJugador,nombreSimple,abreviacion,localizacion,nombre,nombreS,abreviacionEdit,localizacionEdit;
    private Button boton;
    private int id;
    private ActivityJugadoresVM vm;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private Equipo equipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_equipo);

        nombreJugador = findViewById(R.id.introducirNombreEquipo);
        nombreSimple = findViewById(R.id.introducirNombreSimple);
        nombre = findViewById(R.id.introducirNombreEquipoEscrito);
        nombreS = findViewById(R.id.introducirNombreSimpleEscrito);
        abreviacion = findViewById(R.id.introducirAbreviacion);
        abreviacionEdit = findViewById(R.id.introducirAbreviacionEscrita);
        localizacion = findViewById(R.id.introducirLocalizacion);
        localizacionEdit = findViewById(R.id.introducirLocalizacionEscrita);
        mPager = findViewById(R.id.pager);
        boton = findViewById(R.id.boton);

        vm = new ViewModelProvider(this).get(ActivityJugadoresVM.class);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        id = bundle.getInt("IDEquipo");

        vm.setIdEquipo(id);
        vm.cargarJugadores();
        vm.getJugadores().observe(this,new Observer<List<Jugador>>()
        {
            @Override
            public void onChanged(@Nullable final List<Jugador> jugadores)
            {
                pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),vm.getJugadores().getValue());
                mPager.setAdapter(pagerAdapter);
            }
        });

        equipo = usarDatabase.getDatabase(this).dao().recogerEquipoPorID(id);
        nombre.setText(equipo.getTeamName());
        nombreS.setText(equipo.getSimpleName());
        abreviacionEdit.setText(equipo.getAbbreviation());
        localizacionEdit.setText(equipo.getLocation());

        boton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,activityIntroducirJugador.class);
        Bundle extras = new Bundle();
        extras.putInt("IDEquipo", equipo.getTeamId());
        intent.putExtras(extras);
        startActivity(intent);
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<Jugador> listjugadores;
        public ViewPagerAdapter(FragmentManager fm, List<Jugador> listjugadores) {
            super(fm);
            this.listjugadores = listjugadores;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentJugadores.newInstance(listjugadores, position);
        }

        @Override
        public int getCount() {
            return listjugadores.size();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}
