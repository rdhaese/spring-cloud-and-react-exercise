package com.rdhaese.springcloudandreactexercise.addressservice.database.search;

public class AddressSearch {

    private String street;
    private Integer number;
    private String numberExtra;
    private String city;
    private String postalCode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNumberExtra() {
        return numberExtra;
    }

    public void setNumberExtra(String numberExtra) {
        this.numberExtra = numberExtra;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
