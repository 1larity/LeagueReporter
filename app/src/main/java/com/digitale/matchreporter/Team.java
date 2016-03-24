package com.digitale.matchreporter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Created by Rich on 15/03/2016.
 * container for match data
 */
public class Team {
    String name;
    String id;
    String manager;
    String formation;
    ArrayList<Player> players = new ArrayList<>();
    /**
     * Blank Constructor
     */
    public Team() {
    }
    /**
     * JSON Constructor
     */
    public Team(JSONObject jTeam) {
        JSONArray lPlayers;
        try {
            this.name = jTeam.getString("name");
            this.id = jTeam.getString("id");
            this.manager = jTeam.getString("manager");
            this.formation = jTeam.getString("formation");
            lPlayers = jTeam.getJSONArray("team");
            //create player records using reflection and Google Json decoder
            java.lang.reflect.Type type =
                    new com.google.gson.reflect.TypeToken<ArrayList<Player>>() {
                    }.getType();
            this.players = new Gson().fromJson(String.valueOf(lPlayers), type);
            //set player portrait
            for (int i = 0; i < this.players.size(); i++) {
                if (this.getName().equals("Bournemouth")) {
                    this.players.get(i).setBitmap(MainActivity.mPlayerPortraits.get(i).bitmap);
                } else if (this.getName().equals("Everton")) {
                    this.players.get(i).setBitmap(MainActivity.mPlayerPortraits.get(i + 18).bitmap);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getManager() {
        return manager;
    }
    public String getFormation() {
        return formation;
    }
}
