package com.rdhaese.springcloudandreactexercise.studentservice.proxy;

import com.rdhaese.springcloudandreactexercise.studentservice.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "address-service",
        fallback = AddressServiceProxy.AddressServiceProxyFallback.class
        )
public interface AddressServiceProxy {

    @GetMapping(value = "/address/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    AddressDto getAddress(@PathVariable String uuid);

    @Component
    class AddressServiceProxyFallback implements AddressServiceProxy{

        //This is not very functional, but is just an example
        @Override
        public AddressDto getAddress(String uuid) {
            AddressDto addressDto = new AddressDto();
            addressDto.setUuid("Could not contact address-service");
            return addressDto;
        }
    }
}
