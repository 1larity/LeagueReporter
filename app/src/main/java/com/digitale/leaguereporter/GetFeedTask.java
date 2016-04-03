package com.digitale.leaguereporter;


import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Get JSON data from incrowd Website.
 * Needs to be run as seperate thread so UI thread doesn't get blocked.
 * params[0] The mode of the data fetch (SEASONS or STATS).
 * params[1] The URL of the data.
 */
class GetFeedTask extends AsyncTask<Integer, Void, TaskResult> {
    private static final int NODATA = -2;
    private static final int NETWORKFAIL = -1;
    public static final int SEASONS = 1;
    public static final int LEAGUE = 2;
    public static final int TEAM = 3;
    public static final int PLAYERS = 4;
    public static final int PLAYER = 3;

    public MainActivity activity;

    public GetFeedTask(MainActivity activity) {

        this.activity = activity;
    }

    /**
     * Background data retrieval task
     *
     * @param params first param is flag to indicate if feed is JSON array or object
     *               Second param is the data URL
     */
    @Override
    protected TaskResult doInBackground(Integer... params) {
        TaskResult results = new TaskResult();
        int mode = params[0];
        String getURL = "";
        switch (mode) {
            case SEASONS:
                getURL = activity.getString(R.string.seasonsURL) + "?season=" + String.valueOf(activity.mSeasonYear);
                break;
            case LEAGUE:
                getURL = activity.getString(R.string.seasonsURL) + String.valueOf(activity.mLeagueID) + "/leagueTable";
                System.out.println("getting league URL " + getURL);
                break;
            case TEAM:
                getURL = activity.getString(R.string.teamURL) + String.valueOf(activity.mTeamID);
                System.out.println("getting team URL " + getURL);
                break;
            case PLAYERS:
                getURL = activity.getString(R.string.teamURL) + String.valueOf(activity.mTeamID)+"/players";
                System.out.println("getting players URL " + getURL);
                break;
        }
        //nasty deprecated stuff I need for my phone
        //HttpURLConnection is v buggy Eclair/Froyo
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(getURL);
        get.setHeader("X-Auth-Token", "4fa37bbe1e4741c6a5c5ff0cc8981df6");
        String str = null;

        try {
            if (!isCancelled()) {
                response = myClient.execute(get);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

            if (str != null && !(isCancelled())) {
                if (mode == SEASONS) {
                    System.out.println("SEASON DATA " + str);
                    activity.mDatabase.seasonsFromJson(str);
                }else if (mode == LEAGUE) {
                    System.out.println("LEAGUE DATA " + str);
                    if(str.contains("The resource you are looking for does not exist.")){
                        mode=NODATA;
                    }else{
                        activity.mDatabase.leagueFromJson(str);
                    }
                    }else if (mode == TEAM) {
                    System.out.println("TEAM DATA " + str);

                    activity.mDatabase.teamFromJson(str);
                }else if (mode == PLAYERS){
                    System.out.println("PLAYERS DATA"+str);
                   activity.mDatabase.PlayersFromJson(str);
                }else if (mode == PLAYER){
//                    System.out.println("PLAYER DATA");
                //    activity.mDatabase.PlayerFromJson;
                }

                results.setMode(mode);
            }
        } catch (ClientProtocolException e) {
            results.setMode(NETWORKFAIL);
            e.printStackTrace();
        } catch (IOException e) {
            results.setMode(NETWORKFAIL);
            e.printStackTrace();
        }
        return results;
    }


    public void onPostExecute(TaskResult result) {
        System.out.println("REFRESHUI");
        if (result.getMode() == SEASONS) {
            //update the UI for seasons  data changes
            activity.mSeasons.clear();
            activity.mSeasons.addAll(activity.mDatabase.getSeasons());
            MainActivity.mSeasonAdapter.notifyDataSetChanged();
        } else if (result.getMode() == LEAGUE) {
            //update the UI for commentary data changes
            activity.mStandings.clear();
            activity.mStandings.addAll(activity.mDatabase.getLeague().getStandings());
            MainActivity.mStandingsAdapter.notifyDataSetChanged();
            try {
                activity.mStandingsFragment.invalidateStandingsInfoView(activity);
            } catch (NullPointerException e) {
                //fragment simply doesn't exist yet, not a problem
            }
        } else if(result.getMode()== TEAM){// || result.getMode()== PLAYERS){
            System.out.println("DB TEAM CONTENTS"+activity.mDatabase.getTeam().getName());
                try {
                    activity.mTeamFragment.invalidateTeamView(activity);
                } catch (NullPointerException e) {
                    //fragment simply doesn't exist yet, not a problem
                }

                activity.mPlayerList.clear();
                activity.mPlayerList.addAll(activity.mDatabase.getTeam().getPlayers());
                MainActivity.mPlayerAdapter.notifyDataSetChanged();

        } else if( result.getMode()== PLAYERS){
            if(activity.mDatabase.getTeam().size()>0) {
                activity.mPlayerList.clear();
                activity.mPlayerList.addAll(activity.mDatabase.getTeam().getPlayers());
                MainActivity.mPlayerAdapter.notifyDataSetChanged();
            }
        } else if (result.getMode() == NETWORKFAIL) {
            //Oh Google! Enforced statics in abstract classes make baby jesus cry!
            Toast.makeText(activity.getContext(), "Cannot contact server, please try again later",
                    Toast.LENGTH_LONG).show();
            activity.finish();
        } else if (result.getMode() == NODATA) {
            //Oh Google! Enforced statics in abstract classes make baby jesus cry!
            Toast.makeText(activity.getContext(), "Sorry, we don't have any more data for this.",
                    Toast.LENGTH_LONG).show();
            activity.mViewPager.setCurrentItem(0);
        }
    }

}