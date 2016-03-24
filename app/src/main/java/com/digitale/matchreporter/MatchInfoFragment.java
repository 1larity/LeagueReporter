package com.digitale.matchreporter;

/**
 * Fragment for match information
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
public class MatchInfoFragment extends Fragment implements FragmentNotifier{
    View rootView = null;
    public MatchInfoFragment() {
    }
    @Override
    public void fragmentVisible() {
        //  invalidateCommentaryDetailView((MainActivity) getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match_info, container, false);
        ListView mEventListView = (ListView) rootView.findViewById(R.id.eventListView);
        mEventListView.setAdapter(MainActivity.mEventAdaptor);
        invalidateMatchInfoView((MainActivity) getActivity());
        return rootView;
    }
    public  void invalidateMatchInfoView(MainActivity activity) {
        TextView textCompetition = (TextView) rootView.findViewById(R.id.cCompetition);
        TextView textVenue = (TextView) rootView.findViewById(R.id.cVenueName);
        TextView textKickOff = (TextView) rootView.findViewById(R.id.cKickOff);
        TextView textAttendance = (TextView) rootView.findViewById(R.id.cAttendance);
        TextView textReferee = (TextView) rootView.findViewById(R.id.cReferee);
        TextView textDuration = (TextView) rootView.findViewById(R.id.cDuration);
        textCompetition.setText(activity.mStatsData.matchData.getCompetition());
        textVenue.setText(activity.mStatsData.matchData.getVenue());
        textKickOff.setText(activity.mStatsData.matchData.getMatchTimeAsString());
        textAttendance.setText(String.valueOf(activity.mStatsData.matchData.getAttendance()));
        textReferee.setText(activity.mStatsData.matchData.getReferee());
        textDuration.setText(activity.mStatsData.matchData.getMatchDuration());
    }
}
