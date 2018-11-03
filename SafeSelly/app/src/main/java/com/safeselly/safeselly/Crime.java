package com.safeselly.safeselly;

public class Crime {

    private String category;
    private String location;
    private String date;

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

    public Crime(String category, String location, String date) {
        this.category = category;
        this.location = location;
        this.date = date;
    }
}
