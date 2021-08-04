package com.rdhaese.springcloudandreactexercise.addressservice.controller;

import com.rdhaese.springcloudandreactexercise.addressservice.controller.dto.AddressDto;
import com.rdhaese.springcloudandreactexercise.addressservice.controller.mapper.AddressMapper;
import com.rdhaese.springcloudandreactexercise.addressservice.database.model.Address;
import com.rdhaese.springcloudandreactexercise.addressservice.database.repository.AddressRepository;
import com.rdhaese.springcloudandreactexercise.addressservice.database.search.AddressCriteria;
import com.rdhaese.springcloudandreactexercise.addressservice.database.search.AddressSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    private static final String URL_ROOT = "/address";
    private static final String URL_BY_UUID = URL_ROOT + "/{uuid}";


    private AddressRepository addressRepository;
    private AddressMapper addressMapper;

    public AddressController(
            @Autowired AddressRepository addressRepository,
            @Autowired AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @GetMapping(value = URL_ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> getAddresses(
            @ModelAttribute AddressSearch addressSearch,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        AddressCriteria addressCriteria = new AddressCriteria(addressSearch);

        Pageable pageable = PageRequest.of(page, size);

        Page<Address> addressesPageable = addressRepository.findAll(addressCriteria, pageable);
        List<AddressDto> addressDtos = addressMapper.mapToDto(addressesPageable.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("addresses",addressDtos);
        response.put("currentPage", addressesPageable.getNumber());
        response.put("totalItems", addressesPageable.getTotalElements());
        response.put("totalPages", addressesPageable.getTotalPages());

        return response;
    }

    @GetMapping(value = URL_BY_UUID, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    AddressDto getAddress(@PathVariable String uuid) {
        Address address = addressRepository.getByUuid(uuid);

        if (address == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Address with uuid [%s] does not exist.", uuid)
            );
        } else {
            return addressMapper.mapToDto(address);
        }
    }
}
