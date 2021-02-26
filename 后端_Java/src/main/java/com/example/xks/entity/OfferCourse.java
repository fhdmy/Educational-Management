package com.example.xks.entity;

public class OfferCourse {

    private String tid;

    private String cid;

    private int maxnum;

    private String place;

    private String time;

    private int chosennum;

    public OfferCourse(String cid, String tid, String time) {
        this.cid=cid;
        this.tid=tid;
        this.time=time;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getChosennum() {
        return chosennum;
    }

    public void setChosennum(int chosennum) {
        this.chosennum = chosennum;
    }
}
