package com.digitale.matchreporter;

/**
 * Created by Rich on 15/03/2016.
 * container for player data
 */
class Event {
   private String type;
   private int when;
   private String whom;
   private boolean isHome;

    /**
     * Blank Constructor
     */
    public Event() {
    }
     /**
     * Full Constructor
     * don't need this using reflection to build data
     */
    public Event(String type, int when, String whom, boolean isHome) {
        this.type = type;
        this.when = when;
        this.whom = whom;
        this.isHome = isHome;
    }
    public String getType() {
        return type;
    }

    public int getWhen() {
        return when;
    }

    public String getWhom() {
        return whom;
    }
    public boolean isHome() {
        return isHome;
    }
}
