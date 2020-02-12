package es.iesnervion.victor.nba.Clases;

public class Equipo {
    private int teamId;
    private String abbreviation;
    private String teamName;
    private String simpleName;
    private String location;
    private String imagen;

    public Equipo(){}

    public Equipo(int teamId,String abbreviation, String teamName, String simpleName, String location,String imagen){
        this.teamId = teamId;
        this.abbreviation = abbreviation;
        this.teamName = teamName;
        this.simpleName = simpleName;
        this.location = location;
        this.imagen = imagen;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getLocation() {
        return location;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getImagen(){return imagen;}
}
