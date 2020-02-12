package es.iesnervion.victor.nba.Utilidades;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.iesnervion.victor.nba.Clases.Equipo;
import es.iesnervion.victor.nba.R;

public class Utilidades {

    public InputStream getInputStream(Context context){

        return context.getResources().openRawResource(R.raw.teams);
    }

    public List<Equipo> readJsonStream(InputStream in) throws IOException {
        // Nueva instancia JsonReader

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Equipo> JSONequipos = new ArrayList<Equipo>();
        try {
            // Leer Array
            JSONequipos = leerArrayEquipo(reader);
        } finally {
            reader.close();
        }

        return JSONequipos;
    }

    public List leerArrayEquipo(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList equipos = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            equipos.add(leerEquipo(reader));
        }
        reader.endArray();

        return equipos;
    }

    public Equipo leerEquipo(JsonReader reader) throws IOException {
        String abbreviation = null;
        String teamName = null;
        String simpleName = null;
        String location = null;
        String imagen = null;
        int teamId = 0;
        Equipo equipo = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "teamId":
                    teamId = reader.nextInt();
                break;

                case "teamName":
                    teamName = reader.nextString();
                break;

                case "simpleName":
                    simpleName = reader.nextString();
                break;

                case "abbreviation":
                    abbreviation = reader.nextString();
                break;

                case "location":
                    location = reader.nextString();
                break;

                case "imagen":
                    imagen = reader.nextString();
                break;

                default:
                    reader.skipValue();
                break;
            }
        }
        reader.endObject();
        equipo = new Equipo(teamId,abbreviation,teamName,simpleName,location,imagen);
        return equipo;
    }

}
