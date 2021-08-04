package com.rdhaese.springcloudandreactexercise.addressservice.database.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(indexes = @Index(name = "index_uuid", columnList = "uuid", unique = true))
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String uuid;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private Integer number;
    @Column
    private String numberExtra;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String postalCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        Address address = (Address) o;
        return uuid.equals(address.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
