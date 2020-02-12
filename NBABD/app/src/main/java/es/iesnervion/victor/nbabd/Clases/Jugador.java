package es.iesnervion.victor.nbabd.Clases;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Equipo.class,parentColumns = "teamId",childColumns = "team_id"))
public class Jugador {
    private String firstName;
    private String lastName;

    @PrimaryKey(autoGenerate = true)
    private int playerId;

    @ColumnInfo(name = "team_id")
    private int teamId;

    @Ignore
    public Jugador(){}

    public Jugador(String firstName,String lastName ,int teamId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
    }
    @Ignore
    public Jugador(String firstName,String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Ignore
    public Jugador(Jugador jugador){
        this.firstName = jugador.getFirstName();
        this.lastName = jugador.getLastName();
        this.playerId = jugador.getPlayerId();
        this.teamId = jugador.getPlayerId();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
