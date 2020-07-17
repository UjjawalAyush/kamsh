package com.ujjawalayush.example.kamsh.FirebaseData;

import com.ujjawalayush.example.kamsh.Data.AddpostData;

import java.util.ArrayList;

public class PostData {
    private ArrayList<AddpostData> myList=new ArrayList<>();
    private String title,author,photo;
    private Long time;

    public String getTitle() {
        return title;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<AddpostData> getMyList() {
        return myList;
    }

    public void setMyList(ArrayList<AddpostData> myList) {
        this.myList.addAll(myList);
    }
    public String getAuthor() {
        return author;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
