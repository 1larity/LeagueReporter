package com.digitale.matchreporter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rich on 15/03/2016.
 * container for stats data
 */
public class StatsData {
    MatchData matchData=new MatchData();
    PlayStats homePlayStats=new PlayStats();
    PlayStats awayPlayStats=new PlayStats();
    Team homeTeam=new Team();
    Team awayTeam=new Team();
    ArrayList<Event> events=new ArrayList<>();
    /**
     * Blank Constructor
     */
    public StatsData(){}
    /**
     * JSON Constructor
     */
    public StatsData(JSONObject jData){
        JSONArray lEvents;
        try {
             this.matchData=new MatchData(jData.getString("time"),jData.getString("competition"),
                    jData.getString("venue"),jData.getInt("attendance"),jData.getString("referee"),
                    jData.getLong("timestamp"));

            this.homePlayStats=new PlayStats(jData.getJSONObject("homeStats"));
            this.awayPlayStats=new PlayStats(jData.getJSONObject("awayStats"));
            this.homeTeam=new Team (jData.getJSONObject("home"));
            this.awayTeam=new Team (jData.getJSONObject("away"));
            lEvents = jData.getJSONArray("events");
            //create events records using reflection and Google Json decoder
            java.lang.reflect.Type type =
                    new com.google.gson.reflect.TypeToken<ArrayList<Event>>() {
                    }.getType();
            this.events = new Gson().fromJson(String.valueOf(lEvents), type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
