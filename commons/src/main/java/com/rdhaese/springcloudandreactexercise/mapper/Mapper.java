package com.rdhaese.springcloudandreactexercise.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Mapper<B, D> {

    B mapToBusiness(D dto);

    D mapToDto(B bus);

    default Stream<B> mapToBusiness(Stream<D> dtoStream) {
        return dtoStream.map(dto -> mapToBusiness(dto));
    }

    default List<B> mapToBusiness(List<D> dtoCollection) {
        return mapToBusiness(dtoCollection.stream())
                .collect(Collectors.toList());
    }

    default Stream<D> mapToDto(Stream<B> busStream){
        return busStream.map(bus -> mapToDto(bus));
    }

    default List<D> mapToDto(List<B> busCollection){
        return mapToDto(busCollection.stream())
                .collect(Collectors.toList());
    }
}
