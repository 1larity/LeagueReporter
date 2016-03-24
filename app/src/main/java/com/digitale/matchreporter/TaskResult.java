package com.digitale.matchreporter;

import java.util.ArrayList;

/**
 * Created by Rich on 13/03/2016.
 * container for commentary and stats data
 */
public class TaskResult {
    ArrayList<CommentaryListItem> commentaryList=new ArrayList<>();
    StatsData statsData=new StatsData();
    int mode;
    public int getMode() {

        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Blank Constructor
     */
    public TaskResult() { }
    /**
     * Setter for commentary data
     * @param commentaryList the commentarylist to be passed in results
     */
    public void setCommentaryList(ArrayList<CommentaryListItem> commentaryList) {
        this.commentaryList = commentaryList;
    }
    /**
     * Setter for statistics data
     * @param statsdata the statsdata to be passed in results
     */
    public void setStatsData(StatsData statsdata) {
        this.statsData = statsdata;
    }
    public  ArrayList<CommentaryListItem> getCommentaryList() {
        return commentaryList;
    }

    public StatsData getStatsData() {
        return statsData;
    }

}
