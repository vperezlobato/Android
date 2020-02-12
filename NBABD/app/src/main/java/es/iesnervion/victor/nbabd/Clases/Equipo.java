package es.iesnervion.victor.nbabd.Clases;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Equipo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int teamId;
    private String abbreviation;
    private String teamName;
    private String simpleName;
    private String location;
    @Ignore
    public Equipo(){}

    public Equipo(String abbreviation, String teamName, String simpleName, String location){
        this.abbreviation = abbreviation;
        this.teamName = teamName;
        this.simpleName = simpleName;
        this.location = location;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}
