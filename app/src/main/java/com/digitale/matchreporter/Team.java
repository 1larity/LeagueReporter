package com.digitale.matchreporter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Created by Rich on 24/03/2016.
 * container for team data
 */
public class Team {
    String name;
    String squadMarketValue;
    String crestURL;
    String shortName;
    ArrayList<Player> players = new ArrayList<>();
    /**
     * Blank Constructor
     */
    public Team() {

    }

    public Team(String name, String squadMarketValue, String shortName, String crestURL) {
    this.name=name;
        this.squadMarketValue=squadMarketValue;
        this.shortName=shortName;
        this.crestURL=crestURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSquadMarketValue() {
        return squadMarketValue;
    }

    public void setSquadMarketValue(String squadMarketValue) {
        this.squadMarketValue = squadMarketValue;
    }

    public String getCrestURL() {
        return crestURL;
    }

    public void setCrestURL(String crestURL) {
        this.crestURL = crestURL;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
