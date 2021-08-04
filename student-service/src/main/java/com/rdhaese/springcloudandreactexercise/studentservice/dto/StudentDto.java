package com.rdhaese.springcloudandreactexercise.studentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement (name = "student")
public class StudentDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @JsonProperty(value = "address")
    private AddressDto addressDto;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
