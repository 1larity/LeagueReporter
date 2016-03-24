package com.digitale.matchreporter;

/**
 * Fragment for displaying season list
 * Created by Rich on 24/03/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Season Fragment.
 */
public class SeasonsFragment extends Fragment implements FragmentNotifier{
    public SeasonsFragment() {
    }
    @Override
    public void fragmentVisible() {
        // do animations in here
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_season, container, false);
        ListView seasonListView = (ListView) rootView.findViewById(R.id.seasonListView);
        seasonListView.setAdapter(MainActivity.mSeasonAdaptor);

        seasonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.mLeagueID=MainActivity.mDatabase.getSeasons().get(position).getId();
                System.out.println("POSITION" + position + " league ID " + MainActivity.mLeagueID);
                GetFeedTask leagueAsyncTask = new GetFeedTask((MainActivity)getActivity());
                leagueAsyncTask.execute(GetFeedTask.LEAGUE);
                showLeaguePage((MainActivity) getActivity());
            }
        });
        return rootView;
    }

    private void showLeaguePage(MainActivity activity) {
        activity.mViewPager.setCurrentItem(1);
    }
}