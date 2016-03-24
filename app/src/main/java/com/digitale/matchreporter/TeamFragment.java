package com.digitale.matchreporter;

/**
 * Fragment for displaying match info
 * Created by Rich on 22/03/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Match info Fragment.
 */
public class TeamFragment extends Fragment implements FragmentNotifier{
    View rootView = null;
    public TeamFragment() {
    }
    @Override
    public void fragmentVisible() {
        //invalidateCommentaryDetailView((MainActivity) getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_team, container, false);
        super.onCreate(savedInstanceState);
        String tag = this.getTag();
        tag = tag.substring(tag.lastIndexOf(':') + 1);
        if (tag.equals("3")) {
            ListView mHomeListView = (ListView) rootView.findViewById(R.id.teamListView);
            mHomeListView.setAdapter(MainActivity.mHomePlayerAdaptor);
            invalidateHomeTeamView((MainActivity) getActivity());
        } else if (tag.equals("4")){
            ListView mAwayListView = (ListView) rootView.findViewById(R.id.teamListView);
            mAwayListView.setAdapter(MainActivity.mAwayPlayerAdaptor);
            invalidateAwayTeamView((MainActivity) getActivity());
        }
        return rootView;
    }
    public  void invalidateHomeTeamView(MainActivity activity) {
        TextView textTeamName = (TextView) rootView.findViewById(R.id.cTeamName);
        TextView textManager = (TextView) rootView.findViewById(R.id.cManager);
        TextView textFormation = (TextView) rootView.findViewById(R.id.cFormation);
        textTeamName.setText(activity.mStatsData.homeTeam.getName());
        textManager.setText(activity.mStatsData.homeTeam.getManager());
        textFormation.setText(activity.mStatsData.homeTeam.getFormation());
    }
    public  void invalidateAwayTeamView(MainActivity activity) {
        TextView textTeamName = (TextView) rootView.findViewById(R.id.cTeamName);
        TextView textManager = (TextView) rootView.findViewById(R.id.cManager);
        TextView textFormation = (TextView) rootView.findViewById(R.id.cFormation);
        textTeamName.setText(activity.mStatsData.awayTeam.getName());
        textManager.setText(activity.mStatsData.awayTeam.getManager());
        textFormation.setText(activity.mStatsData.awayTeam.getFormation());
    }
}