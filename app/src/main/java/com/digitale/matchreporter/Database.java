package com.digitale.matchreporter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rich on 15/03/2016.
 * container for season record
 */
class Database {
    public ArrayList<Season> seasons = new ArrayList<>();
    public League league=new League();

    /**
     * Blank Constructor
     */
    public Database() {
    }

    public void seasonsFromJson(String data) {
        ArrayList<Season> seasons;
        java.lang.reflect.Type type =
                new com.google.gson.reflect.TypeToken<ArrayList<Season>>() {
                }.getType();
        seasons = new Gson().fromJson(data, type);
        this.setSeasons(seasons);
//debug loop
        for (Season currentSeason : this.getSeasons()) {
            System.out.println("Season " + currentSeason.getCaption());
        }
    }

    public void leagueFromJson(String data) {
        JSONArray jStandings;
        try {
            JSONObject jLeague=new JSONObject(data);
            this.league=new League(jLeague.getString("leagueCaption"),
                    jLeague.getInt("matchday"));
            jStandings=jLeague.getJSONArray("standing");
            java.lang.reflect.Type type =
                    new com.google.gson.reflect.TypeToken<ArrayList<Standing>>() {
                    }.getType();
            this.league.standings = new Gson().fromJson(String.valueOf(jStandings), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(Standing currentStanding:this.league.getStandings()){
            System.out.println("Team Data "+ currentStanding.getTeamName() +" AWAY DRAWS " + currentStanding.getAwayStats().getDraws());
        }
    }
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
    public League getLeague() {
        return league;
    }

    public void setLeague (League league) {
        this.league = league;
    }

}
