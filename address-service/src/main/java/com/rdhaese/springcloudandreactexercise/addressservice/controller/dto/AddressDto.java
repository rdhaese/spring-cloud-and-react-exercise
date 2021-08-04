package com.rdhaese.springcloudandreactexercise.addressservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonRootName("address")
public class AddressDto {

    @NotBlank
    private String uuid;
    @NotBlank
    private String street;
    @NotNull
    @Min(1)
    private Integer number;
    private String numberExtra;
    @NotBlank
    private String city;
    @NotBlank
    private String postalCode;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDto that = (AddressDto) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
