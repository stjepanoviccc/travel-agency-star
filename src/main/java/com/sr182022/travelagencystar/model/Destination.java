package com.sr182022.travelagencystar.model;

public class Destination {
    private int id;
    private String city;
    private String country;
    private String continent;
    private String image;

    public Destination() {}

    public Destination(int id, String city, String country, String continent, String image) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.continent = continent;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return city;
    }

    public String toFileString() {
        return this.getId() + "|" + this.getCity() + "|" + this.getCountry() + "|" + this.getContinent() + "|" + this.getImage();
    }

}