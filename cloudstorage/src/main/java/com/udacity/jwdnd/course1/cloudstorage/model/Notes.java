package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {

    private Integer noteid;
    private String title;

    private String description;


    private Integer userid;

    public Notes(Integer noteid, String title, String description, Integer userid) {
        this.noteid = noteid;
        this.title = title;
        this.description = description;
        this.userid = userid;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
