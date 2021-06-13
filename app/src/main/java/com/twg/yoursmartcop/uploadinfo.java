package com.twg.yoursmartcop;

public class uploadinfo {
    public String imageName;
    public String imageURL;
    public  String location;
    public  String time;
    double longitude;
    double latitude;
    public uploadinfo(){}

    public uploadinfo(String imageName, String imageURL, String location, String time, double longitude, double latitude) {
        this.imageName = imageName;
        this.imageURL = imageURL;
        this.location = location;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
