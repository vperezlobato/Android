package es.iesnervion.victor.nbabd.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.iesnervion.victor.nbabd.Clases.Equipo;
import es.iesnervion.victor.nbabd.Room.usarDatabase;

public class MainActivityVM extends AndroidViewModel {
    private LiveData<List<Equipo>> equipos;


    public MainActivityVM(Application app){
        super(app);
        equipos = usarDatabase.getDatabase(getApplication()).dao().recogerEquipos();
    }

    public LiveData<List<Equipo>> getEquipos(){

        return equipos;
    }

}
