package com.digitale.matchreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rich on 13/03/2016.
 * Custom list adaptor for event details.
 */
public class EventAdapter extends BaseAdapter {
    private ArrayList<Event> mData;
        private static LayoutInflater inflater = null;

        public EventAdapter(Context context, ArrayList<Event> data) {
            this.mData = data;
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
                itemView = (RelativeLayout) inflater.inflate(R.layout.event_list_row, parent,false);
            }else {
                itemView = (RelativeLayout) convertView;
            }
            String homeAway;
            if(mData.get(position).isHome()) {
                homeAway = "home";
            }else{
                homeAway = "away";
            }

            TextView textHeading = (TextView) itemView.findViewById(R.id.cEventHeading);
            textHeading.setText(mData.get(position).getType());
            TextView textDetail = (TextView) itemView.findViewById(R.id.cEventContent);
            textDetail.setText(mData.get(position).getWhom()+ " ("+homeAway+")");
            TextView textTime = (TextView) itemView.findViewById(R.id.cEventTime);
            textTime.setText(String.valueOf(mData.get(position).getWhen())+"'");
            return itemView;
        }

    }


