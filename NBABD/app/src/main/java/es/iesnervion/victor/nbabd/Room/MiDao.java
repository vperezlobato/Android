package es.iesnervion.victor.nbabd.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.iesnervion.victor.nbabd.Clases.Equipo;
import es.iesnervion.victor.nbabd.Clases.Jugador;

@Dao
public interface MiDao{

    @Query("SELECT * FROM Equipo")
    public LiveData<List<Equipo>> recogerEquipos();

    @Query("SELECT * FROM Equipo Where teamId = :id")
    public Equipo recogerEquipoPorID(int id);

    @Insert
    public void insertarEquipo(Equipo equipo);


    @Query("Select * From Jugador Where team_id = :idEquipo")
    public List<Jugador> recogerJugadores(int idEquipo);

    @Insert
    public void insertarJugador(Jugador jugador);

}
