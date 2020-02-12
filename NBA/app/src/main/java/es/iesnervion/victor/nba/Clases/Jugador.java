package es.iesnervion.victor.nba.Clases;

public class Jugador {
    private String firstName;
    private String lastName;
    private int playerId;
    private int teamId;

    public Jugador(){}

    public Jugador(String firstName,String lastName,int playerId,int teamId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerId = playerId;
        this.teamId = teamId;
    }

    public Jugador(Jugador jugador){
        this.firstName = jugador.getFirstName();
        this.lastName = jugador.getLastName();
        this.playerId = jugador.getPlayerId();
        this.teamId = jugador.getPlayerId();
    }

    public int getPlayerId() {
        return playerId;
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

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
