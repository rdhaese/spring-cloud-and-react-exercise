package com.rdhaese.springcloudandreactexercise.addressservice.database.repository;

import com.rdhaese.springcloudandreactexercise.addressservice.database.model.Address;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

    @Cacheable("addresses")
    Address getByUuid(String uuid);
}
