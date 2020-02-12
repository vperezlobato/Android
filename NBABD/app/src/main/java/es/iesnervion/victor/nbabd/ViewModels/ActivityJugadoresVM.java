package es.iesnervion.victor.nbabd.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import es.iesnervion.victor.nbabd.Clases.Jugador;
import es.iesnervion.victor.nbabd.Room.usarDatabase;

public class ActivityJugadoresVM extends AndroidViewModel {

    @NonNull
    private MutableLiveData<List<Jugador>> jugadores;
    private Integer idEquipo;

    public ActivityJugadoresVM(@NonNull Application app){
        super(app);
        idEquipo = 0;
        jugadores = new MutableLiveData<List<Jugador>>();
    }

    public LiveData<List<Jugador>> getJugadores(){

        return jugadores;
    }

    public Integer getIdEquipo(){

        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo){
        this.idEquipo = idEquipo;

    }

    public void cargarJugadores(){
        List<Jugador> listadoJugadores = new ArrayList<Jugador>(usarDatabase.getDatabase(getApplication()).dao().recogerJugadores(getIdEquipo()));
        jugadores.setValue(listadoJugadores);
    }


}
