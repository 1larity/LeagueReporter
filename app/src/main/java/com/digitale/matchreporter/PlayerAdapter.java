package com.digitale.matchreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Rich on 13/03/2016.
 * Custom list adaptor for commentary details.
 * TODO need to add list view holder to improve scroll performance
 */
public class PlayerAdapter extends BaseAdapter {
        Context context;
    private ArrayList<Player> mData;
        private static LayoutInflater inflater = null;

        public PlayerAdapter(Context context, ArrayList<Player> data) {
            this.context = context;
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
                itemView = (RelativeLayout) inflater.inflate(R.layout.player_list_row,  parent,false);
            }
            else {
                itemView = (RelativeLayout) convertView;
            }
            //set basic player data
            ImageView playerImage = (ImageView) itemView.findViewById(R.id.playerImage);
            playerImage.setImageBitmap(mData.get(position).getBitmap());
            TextView textHeading = (TextView) itemView.findViewById(R.id.cPlayerName);
            textHeading.setText(mData.get(position).getName());
            TextView textDetail = (TextView) itemView.findViewById(R.id.cNumber);
            textDetail.setText(mData.get(position).getNumber());
            TextView textRole = (TextView) itemView.findViewById(R.id.cRole);
            textRole.setText(mData.get(position).getPosition());

            //set cards (presumably player data elements can contain field "redCard" as well as yellowCard)
            //Also this check should be iterative since players can receive multiple yellow cards.
            TextView textCards = (TextView) itemView.findViewById(R.id.cCards);
            if (mData.get(position).getYellowCard() != null && mData.get(position).getYellowCard().length()>0 ) {

                textCards.setText("Recieved yellow card in the " + mData.get(position).getYellowCard() +
                        numberpostfix(mData.get(position).getYellowCard())+" minute.");
            }else{
                //If no card was awarded we need to blank the text to prevent object reuse displaying incorrect data
                textCards.setText("");
            }

            //Set substitution information if required
            TextView textSubstitute = (TextView) itemView.findViewById(R.id.cSubstitution);
            if (mData.get(position).getWhenSubstituted() != null && mData.get(position).getWhenSubstituted().length()>0) {
                        textSubstitute.setText("Substituted in the " + mData.get(position).getWhenSubstituted() +
                                numberpostfix(mData.get(position).getWhenSubstituted())+ " minute by " + mData.get(position).getWhichSubstitute() + ".");
            }else{
                //If no sub occurred we need to blank the text to prevent object reuse displaying incorrect data
                textSubstitute.setText("");
            }

            return itemView;
        }
    // For bonus points we should parse the time the event occurred so the appropriate "th", "nd","st","rd" postfix
    // is substituted in the text.
    private String numberpostfix(String timeString) {
    int time=Integer.valueOf(timeString.substring(timeString.length()-1,timeString.length()));
        switch (time){
            case 0:
                return "th";
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

}


