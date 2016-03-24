package com.digitale.matchreporter;

/**
 * Fragment for league standings information
 * Created by Rich on 24/03/2016.
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
public class StandingsFragment extends Fragment implements FragmentNotifier{
    View rootView = null;
    public StandingsFragment() {
    }
    @Override
    public void fragmentVisible() {
        //  do animation and frills in here
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_league, container, false);
        ListView mEventListView = (ListView) rootView.findViewById(R.id.leagueListView);
        mEventListView.setAdapter(MainActivity.mStandingsAdaptor);
        invalidateStandingsInfoView((MainActivity) getActivity());
        return rootView;
    }
    public  void invalidateStandingsInfoView(MainActivity activity) {
        TextView textCompetition = (TextView) rootView.findViewById(R.id.cCompetition);
        textCompetition.setText(activity.mDatabase.getLeague().getName());

    }
}
