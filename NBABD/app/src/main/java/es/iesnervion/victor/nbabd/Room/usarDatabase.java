package es.iesnervion.victor.nbabd.Room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.iesnervion.victor.nbabd.Clases.Equipo;
import es.iesnervion.victor.nbabd.Clases.Jugador;

@Database(entities = {Equipo.class,Jugador.class}, version = 2, exportSchema = false)
public abstract class usarDatabase extends RoomDatabase {
    private static usarDatabase INSTANCE;

    public abstract MiDao dao();

    public static usarDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (usarDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            usarDatabase.class, "nbaDB").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
