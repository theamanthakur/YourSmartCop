package com.twg.yoursmartcop;

public class uploadinfo {
    public String imageName;
    public String imageURL;
    public  String location;
    public  String time;
    public uploadinfo(){}

    public uploadinfo(String name, String url,String location,String time) {
        this.imageName = name;
        this.imageURL = url;
        this.location = location;
        this.time = time;
    }

    public String getImageName() {
        return imageName;
    }
    public String getImageURL() {
        return imageURL;
    }
    public String getLocation() {
        return location;
    }
    public String getTime() {
        return time;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
