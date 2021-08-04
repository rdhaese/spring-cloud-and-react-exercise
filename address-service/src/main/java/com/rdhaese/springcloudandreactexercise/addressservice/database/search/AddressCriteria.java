package com.rdhaese.springcloudandreactexercise.addressservice.database.search;

import com.rdhaese.springcloudandreactexercise.addressservice.database.model.Address;
import com.rdhaese.springcloudandreactexercise.addressservice.database.model.Address_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class AddressCriteria implements Specification<Address> {

    private AddressSearch criteria;

    public AddressCriteria(AddressSearch criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Path<String> street = root.get(Address_.street);
        Path<Integer> number = root.get(Address_.number);
        Path<String> numberExtra = root.get(Address_.numberExtra);
        Path<String> city = root.get(Address_.city);
        Path<String> postalCode = root.get(Address_.postalCode);

        if (criteria.getStreet() != null) {
            predicates.add(criteriaBuilder.like(street, "%" + criteria.getStreet() + "%"));
        }
        if (criteria.getNumber() != null) {
            predicates.add(criteriaBuilder.equal(number, criteria.getNumber()));
        }
        if (criteria.getNumberExtra() != null) {
            predicates.add(criteriaBuilder.like(numberExtra, "%" + criteria.getNumberExtra() + "%"));
        }
        if (criteria.getCity() != null) {
            predicates.add(criteriaBuilder.like(city, "%" + criteria.getCity() + "%"));
        }
        if (criteria.getPostalCode() != null) {
            predicates.add(criteriaBuilder.like(postalCode, "%" + criteria.getPostalCode() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
