package com.example.xks.entity;

public class ChooseCourse {

    private String sid;

    private String cid;

    private String tid;

    private String time;

    public ChooseCourse(String sid,String cid,String tid,String time){
        this.sid=sid;
        this.cid=cid;
        this.tid=tid;
        this.time=time;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
