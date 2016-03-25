package com.digitale.matchreporter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    /**
     * Debug members
     */
    public static final boolean DEBUG = false;
    private static final String TAG ="MATCHREPORTER" ;
    /**
     * UI members
     */
    public static UI ui=new UI();
    public static ArrayList<PlayerPortrait> mPlayerPortraits = new ArrayList<>();
    private static SectionsPagerAdapter mSectionsPagerAdapter;

    public ViewPager mViewPager;
    public static MainActivity mActivity;
    public StandingsFragment mStandingsFragment;
    public SeasonsFragment mCommentaryFragment;
    public MatchInfoFragment mMatchInfoFragment;
    public TeamFragment mTeamFragment =new TeamFragment();
    /**
     * Data members
     */
    public static Database mDatabase=new Database();
    ArrayList <Season>mSeasons=new ArrayList<>();
    static int mLeagueID=398;
    int mSeasonYear=2015;
    //data sourc team id
    static int mTeamID=66;
    //internal database id
    public static int mTeamIndex;
    ArrayList <Standing>mStandings=new ArrayList<>();
    public  ArrayList<CommentaryListItem> mCommentaryList = new ArrayList<>();
    public  ArrayList<Player> mHomeList = new ArrayList<>();
    public  ArrayList<Player> mAwayList = new ArrayList<>();
    public  ArrayList<Event> mEventList = new ArrayList<>();
    /**
     * Logic members
     */
    private Handler mRefreshHandler = new Handler();
    public static SeasonAdapter mSeasonAdaptor;
    public static StandingsAdapter mStandingsAdaptor;
    public static PlayerAdapter mAwayPlayerAdaptor;
    public static PlayerAdapter mHomePlayerAdaptor;
    public static EventAdapter mEventAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load save preferences
        prefsLoad();
        //start splash screen
        //  launchSplash();
        setContentView(R.layout.activity_main);
        //load player portraits
        loadImages();

        mSeasonAdaptor = new SeasonAdapter(this,mSeasons);
        mStandingsAdaptor= new StandingsAdapter(this,mStandings);
        mAwayPlayerAdaptor =new PlayerAdapter(this, mAwayList);
        mHomePlayerAdaptor =new PlayerAdapter(this, mHomeList);
        mEventAdaptor=new EventAdapter(this,mEventList);
        mActivity = this;
        ui.onActivityCreateSetTheme(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
       // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        if (mViewPager != null) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }
            @Override
            public void onPageSelected(final int i) {
                FragmentNotifier fragment = (FragmentNotifier) mSectionsPagerAdapter.instantiateItem(mViewPager, i);
                if (fragment != null) {
                    fragment.fragmentVisible();
                }
            }
            @Override
            public void onPageScrollStateChanged(final int i) {
            }
        });

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
        refreshData();

      //  initRefreshHandler();
    }
    //setup and launch splash screen
    private void launchSplash() {
        Intent intent = new Intent(MainActivity.this,SplashActivity.class);
        intent.putExtra("SKIN",String.valueOf(ui.getSkinID()));
        MainActivity.this.startActivity(intent);
    }
    /**
     * load images of players
     * this could also be done from URL source in asynch task
     */
    public void loadImages() {
        LoadImagesTask imageTask = new LoadImagesTask(this);
        imageTask.execute();
    }
    /**
     * create handler to refresh data every 10 seconds
     */
    private void initRefreshHandler(){
        //runnable to periodically update data and invalidate display
        Runnable runnable = new Runnable() {
            public void run() {
                refreshData();
                //repost runnable to execute again in the future
                mRefreshHandler.postDelayed(this, 10000);
            }

        };
        mRefreshHandler.postDelayed(runnable, 10000);
    }
    /**
     * get data and refresh UI
     */
    private void refreshData() {
        GetFeedTask seasonsAsyncTask = new GetFeedTask(this);
       seasonsAsyncTask.execute(GetFeedTask.SEASONS);
        GetFeedTask leagueAsyncTask = new GetFeedTask(this);
        leagueAsyncTask.execute(GetFeedTask.LEAGUE);
        GetFeedTask teamAsyncTask = new GetFeedTask(this);
        teamAsyncTask.execute(GetFeedTask.TEAM);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override

    public void onResume() {
        super.onResume();
        loadImages();
      //  initRefreshHandler();
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        mRefreshHandler.removeCallbacksAndMessages(null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int lSkinID;
        switch (item.getItemId()) {
            case R.id.bournmouthSkin:
                lSkinID=UI.THEME_BOURNMOUTH;
                break;
            case R.id.evertonSkin:
                lSkinID=UI.THEME_EVERTON;
                break;
            default:
                lSkinID=UI.THEME_DEFAULT;
        }
        ui.setSkin(item, lSkinID, this);
        prefsSave(lSkinID);
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment fragment = null;
            switch(position) {
                case 0:
                    fragment= new SeasonsFragment();
                    break;
                case 1:
                    fragment= new StandingsFragment();
                    break;
                case 2:
                    fragment= new TeamFragment();
                    break;
                case 3:
                    fragment= new TeamFragment ();
                    break;
                case 4:
                    fragment= new TeamFragment ();
                    break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            return 5;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    mCommentaryFragment = (SeasonsFragment) createdFragment;
                    break;
                case 1:
                    mStandingsFragment = (StandingsFragment) createdFragment;
                    break;
                case 2:
                    mTeamFragment = (TeamFragment) createdFragment;
                    break;
                case 3:
                 //   mTeamFragment = (TeamFragment) createdFragment;
                    break;
                case 4:
               //     mPlayerFragment = (PlayerFragment) createdFragment;
                    break;
            }
            return createdFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Leagues";
                case 1:
                    return "League Teams";
                case 2:
                    return "Team\nInfo";
                case 3:
                    return "Home\nTeam";
                case 4:
                    return "Away\nTeam";
            }
            return null;
        }

    }
    /**
     * Load Preferences
     */
    public void prefsLoad( ) {
        SharedPreferences lPrefs = getPreferences(MODE_PRIVATE);
        int lSkinID = lPrefs.getInt("mSkinID", 0);
        ui.setSkin(lSkinID,this);
        if (DEBUG) Log.d(TAG, "mSkinID= " + lSkinID);
    }
    /**
     * Save Preferences
     */
    public void prefsSave(int lSkinID) {
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("mSkinID", lSkinID);
        ed.apply();
    }
    public static Context getContext(){
        return mActivity.getApplicationContext();
    }

}
