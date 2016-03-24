package com.digitale.matchreporter;

import android.graphics.Bitmap;

/**
 * Created by Rich on 15/03/2016.
 * container for player data
 */
public class Player {
    String name;
    String number;
    String position;
    String formationPlace;
    String yellowCard;
    String redCard;
    String whenSubstituted;
    String whichSubstitute;
    Bitmap bitmap;
    /**
     * Full Constructor
     * don't need this using reflection to build data
     */
    public Player(String name,String number,String position,String formationPlace,
                  String yellowCard,String redCard, String whenSubstituted, String whichSubstitute) {

            this.name = name;
            this.number = number;
            this.position = position;
            this.formationPlace = formationPlace;
            this.yellowCard = yellowCard;
            this.redCard = redCard;
            this.whenSubstituted =whenSubstituted;
            this.whichSubstitute=whichSubstitute;

    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getPosition() {
        return position;
    }

    public String getYellowCard() {
        return yellowCard;
    }


    public String getWhenSubstituted() {
        return whenSubstituted;
    }

    public String getWhichSubstitute() {
        return whichSubstitute;
    }

}
