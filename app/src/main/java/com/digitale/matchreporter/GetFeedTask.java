package com.digitale.matchreporter;


import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Collections;

/**
 * Get JSON data from incrowd Website.
 * Needs to be run as seperate thread so UI thread doesn't get blocked.
 * params[0] The mode of the data fetch (SEASONS or STATS).
 * params[1] The URL of the data.
 */
class GetFeedTask extends AsyncTask<Integer, Void, TaskResult> {
    public static final int SEASONS = 1;
    public static final int LEAGUE = 2;
    private static final int NETWORKFAIL = 5;
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
                }
                if (mode == LEAGUE) {
                    System.out.println("LEAGUE DATA " + str);
                    activity.mDatabase.leagueFromJson(str);
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
        if (result.getMode() == SEASONS) {
            //update the UI for seasons  data changes
            activity.mSeasons.clear();
            activity.mSeasons.addAll(activity.mDatabase.getSeasons());
            MainActivity.mSeasonAdaptor.notifyDataSetChanged();
        } else if (result.getMode() == LEAGUE) {
            //update the UI for commentary data changes
            activity.mStandings.clear();
            activity.mStandings.addAll(activity.mDatabase.getLeague().getStandings());
            MainActivity.mStandingsAdaptor.notifyDataSetChanged();
            try {
                activity.mStandingsDetailFragment.invalidateStandingsInfoView(activity);
            } catch (NullPointerException e) {
                //fragment simply doesn't exist yet, not a problem
            }

        } else if (result.getMode() == 100) {
            //update the UI for statistics data changes
            activity.mStatsData = result.statsData;
            activity.mHomeList.clear();
            activity.mHomeList.addAll(activity.mStatsData.homeTeam.players);
            activity.mAwayList.clear();
            activity.mAwayList.addAll(activity.mStatsData.awayTeam.players);
            activity.mEventList.clear();
            activity.mEventList.addAll(activity.mStatsData.events);
            try {
                activity.mMatchInfoFragment.invalidateMatchInfoView(activity);
                activity.mHomeTeamFragment.invalidateHomeTeamView(activity);
                activity.mAwayTeamFragment.invalidateAwayTeamView(activity);
                MainActivity.mAwayPlayerAdaptor.notifyDataSetChanged();
                MainActivity.mHomePlayerAdaptor.notifyDataSetChanged();
                MainActivity.mEventAdaptor.notifyDataSetChanged();
            } catch (NullPointerException e) {
                //fragment simply doesn't exist yet, not a problem
            }
        } else if (result.getMode() == NETWORKFAIL) {
            //Oh Google! Enforced statics in abstract classes make baby jesus cry!
            Toast.makeText(activity.getContext(), "Cannot contact server, please try again later",
                    Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

}