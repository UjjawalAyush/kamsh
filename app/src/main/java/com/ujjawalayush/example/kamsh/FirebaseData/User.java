package com.ujjawalayush.example.kamsh.FirebaseData;

public class User {
    String username,email,number,uid,photourl;

    public String getUsername() {
        return username;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}
