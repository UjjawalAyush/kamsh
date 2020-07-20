package com.ujjawalayush.example.kamsh.FirebaseData;

public class ContestData {
    private String startdate,starttime,enddate,endtime,description,prizes,name,type;
    private String uri,user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getPrizes() {
        return prizes;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setPrizes(String prizes) {
        this.prizes = prizes;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setUri(String s) {
        this.uri=s;
    }

    public String getUri() {
        return uri;
    }
}
