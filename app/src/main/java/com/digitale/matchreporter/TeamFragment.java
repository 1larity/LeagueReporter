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
public class TeamFragment extends Fragment implements FragmentNotifier {
    View rootView = null;

    public TeamFragment() {
    }

    @Override
    public void fragmentVisible() {
        //do animation here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_team, container, false);
        super.onCreate(savedInstanceState);
        String tag = this.getTag();
        tag = tag.substring(tag.lastIndexOf(':') + 1);

        ListView mHomeListView = (ListView) rootView.findViewById(R.id.teamListView);
        mHomeListView.setAdapter(MainActivity.mHomePlayerAdaptor);
        invalidateTeamView((MainActivity) getActivity());

        return rootView;
    }

    public void invalidateTeamView(MainActivity activity) {
        if (rootView != null) {
            TextView textTeamName = (TextView) rootView.findViewById(R.id.cTeamName);
            TextView textValue = (TextView) rootView.findViewById(R.id.cSquadValue);
            TextView textGoals = (TextView) rootView.findViewById(R.id.cGoalDetails);
            TextView textPoints = (TextView) rootView.findViewById(R.id.cWins);
            textTeamName.setText(activity.mDatabase.team.getName() + " (" +
                    activity.mDatabase.team.getShortName() + ")");
            if(activity.mDatabase.team.getSquadMarketValue()!=null) {
                textValue.setText(activity.mDatabase.team.getSquadMarketValue());
            }else {
                textValue.setText("Unknown");
            }
            textGoals.setText(MainActivity.mDatabase.getLeague().getStandings().get(MainActivity.mTeamIndex).getGoals() +
                    ". Goals against " + MainActivity.mDatabase.getLeague().getStandings().get(MainActivity.mTeamIndex).getGoalsAgainst() +
                    ". Goal difference " + MainActivity.mDatabase.getLeague().getStandings().get(MainActivity.mTeamIndex).getGoalDifference());
            textPoints.setText( MainActivity.mDatabase.getLeague().getStandings().get(MainActivity.mTeamIndex).getPoints() +
                            ". Wins " + MainActivity.mDatabase.getLeague().getStandings().get(MainActivity.mTeamIndex).getWins() +
                            ". Losses " +MainActivity.mDatabase.getLeague().getStandings().get(MainActivity.mTeamIndex).getLosses() +
                            ". Draws " + MainActivity.mDatabase.getLeague().getStandings().get(MainActivity.mTeamIndex).getDraws());
        }
    }
}