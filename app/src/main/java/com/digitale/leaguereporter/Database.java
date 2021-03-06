package com.digitale.leaguereporter;

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
    public Team team=new Team();

    /**
     * Blank Constructor
     */
    public Database() {
    }
    // get list of leagues active this season
    public void seasonsFromJson(String data) {
        ArrayList<Season> seasons;
        java.lang.reflect.Type type =
                new com.google.gson.reflect.TypeToken<ArrayList<Season>>() {
                }.getType();
        seasons = new Gson().fromJson(data, type);
        this.setSeasons(seasons);
//debug loop
//        for (Season currentSeason : this.getSeasons()) {
//            System.out.println("Season " + currentSeason.getCaption());
//        }
    }
//get list of teams in the league
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
            System.out.println(String.valueOf(jStandings));
            this.league.standings = new Gson().fromJson(String.valueOf(jStandings), type);
            System.out.println(String.valueOf(jStandings));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//debug loop
//        for(Standing currentStanding:this.league.getStandings()){
//            System.out.println("Team Data "+ currentStanding.getTeamName() +
//                    "\nTEAM LINK" + currentStanding.get_links().getTeam().getHref());
//        }
    }
    //get basic team information
    public void teamFromJson(String data) {
        this.team=new Team();
        try {
            JSONObject jTeam=new JSONObject(data);
            this.team=new Team(jTeam.getString("name"),
                    jTeam.getString("squadMarketValue"),
                    jTeam.getString("shortName"),
                    jTeam.getString("crestUrl"));
              } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("NEW TEAM DATA "+ this.getTeam().getName() );
    }
    //get list of players in team
    public void PlayersFromJson(String data) {
        JSONArray jPlayers;
        try {
            JSONObject jTeam = new JSONObject(data);
            this.team.setPlayerCount(jTeam.getInt("count"));
            jPlayers = jTeam.getJSONArray("players");
            java.lang.reflect.Type type =
                    new com.google.gson.reflect.TypeToken<ArrayList<Player>>() {
                    }.getType();
            System.out.println(String.valueOf(jPlayers));
            this.team.players = new Gson().fromJson(String.valueOf(jPlayers), type);
            System.out.println(String.valueOf(jPlayers));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//debug loop
//        for(Player currentPlayer:this.team.getPlayers()){
//            System.out.println("PLAYER Data "+ currentPlayer.getName());
//        }
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
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team=team;
    }
}
