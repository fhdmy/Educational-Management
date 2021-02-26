package com.example.xks.entity;

public class OCourse {

    public OCourse(String cid,String cname,String score,String tid,String tname){
        this.cid=cid;
        this.cname=cname;
        this.score=score;
        this.tid=tid;
        this.tname=tname;
    }

    private int chosennum;

    private int maxnum;

    private String cid;

    private String cname;

    private String tid;

    private String tname;

    private String time;

    private String place;

    private String score;

    private boolean chosen;

    public int getChosennum() {
        return chosennum;
    }

    public void setChosennum(int chosennum) {
        this.chosennum = chosennum;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
