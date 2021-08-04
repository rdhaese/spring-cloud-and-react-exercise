package com.rdhaese.springcloudandreactexercise.addressservice.controller.mapper;

import com.rdhaese.springcloudandreactexercise.addressservice.controller.dto.AddressDto;
import com.rdhaese.springcloudandreactexercise.addressservice.database.model.Address;
import com.rdhaese.springcloudandreactexercise.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<Address, AddressDto> {

    @Override
    public Address mapToBusiness(AddressDto dto) {
        if (dto == null) {
            return null;
        } else {
            Address address = new Address();
            address.setUuid(dto.getUuid());
            address.setStreet(dto.getStreet());
            address.setNumber(dto.getNumber());
            address.setNumberExtra(dto.getNumberExtra());
            address.setCity(dto.getCity());
            address.setPostalCode(dto.getPostalCode());
            return address;
        }
    }

    @Override
    public AddressDto mapToDto(Address bus) {
        if (bus == null) {
            return null;
        } else {
            AddressDto dto = new AddressDto();
            dto.setUuid(bus.getUuid());
            dto.setStreet(bus.getStreet());
            dto.setNumber(bus.getNumber());
            dto.setNumberExtra(bus.getNumberExtra());
            dto.setCity(bus.getCity());
            dto.setPostalCode(bus.getPostalCode());
            return dto;
        }
    }
}
