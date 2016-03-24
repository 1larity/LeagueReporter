package com.digitale.matchreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rich on 13/03/2016.
 * Custom list adaptor for commentary details.
 */
public class SeasonAdapter extends BaseAdapter {

    private ArrayList<Season> mData;
        private static LayoutInflater inflater = null;

        public SeasonAdapter(Context context, ArrayList<Season> data) {
            this.mData = data;
//            for (Season currentSeason : this.mData) {
//                System.out.println("ADAPTOR Seasons " + currentSeason.getCaption());
//            }
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }



    @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout itemView;
            if (convertView == null) {
                itemView = (RelativeLayout) inflater.inflate(R.layout.season_list_row,  parent,false);
            }else {
                itemView = (RelativeLayout) convertView;
            }
            TextView textLeagueName = (TextView) itemView.findViewById(R.id.cLeagueName);
            textLeagueName.setText(mData.get(position).getCaption());
            TextView textDetail = (TextView) itemView.findViewById(R.id.cTeamDetails);
            textDetail.setText("Participating teams "+mData.get(position).getNumberOfTeams()+
                                ".\nNumber of games "+ mData.get(position).getNumberOfGames()+
                                ".\nLast update "+mData.get(position).getLastUpdated());
            //TextView textTime = (TextView) itemView.findViewById(R.id.time);
            //textTime.setText(mData.get(position).getTime());
            return itemView;
        }

    }


