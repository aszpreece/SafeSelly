package com.safeselly.safeselly;

public class Crime {

    private String category;
    private String location;
    private double lat;
    private double lng;
    private String date;
    private int id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Crime(String category, String location, String date, double lat, double lng, int id) {
        this.category = category;
        this.location = location;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
