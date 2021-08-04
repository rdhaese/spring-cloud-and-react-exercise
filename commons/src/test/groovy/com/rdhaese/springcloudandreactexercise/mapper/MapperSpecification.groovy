package com.rdhaese.springcloudandreactexercise.mapper

import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * This class tests the default mapping methods in the {@link Mapper} interface.
 * Simple example classes are provided to be used in the tests.
 */
class MapperSpecification extends Specification {

    private Mapper<TestBus, TestDto> mapper = new SimpleMapper()

    def "Mapping a dto stream to a business stream calls the single object method correctly"() {
        given:
        TestDto dto1 = new TestDto("1")
        TestDto dto2 = new TestDto("2")
        Stream<TestDto> dtoStream = Stream.of(dto1, null, dto2)

        when:
        Stream<TestBus> busStream = mapper.mapToBusiness(dtoStream)
        List<TestBus> result = busStream.collect(Collectors.toList())

        then:
        result.get(0).property == dto1.property
        result.get(1) == null
        result.get(2).property == dto2.property
    }

    def "Mapping a dto list to a business list calls the stream method correctly"() {
        given:
        TestDto dto1 = new TestDto("1")
        TestDto dto2 = new TestDto("2")
        List<TestDto> dtoList = new ArrayList<>()
        dtoList.add(dto1)
        dtoList.add(null)
        dtoList.add(dto2)

        when:
        List<TestBus> result = mapper.mapToBusiness(dtoList)

        then:
        result.get(0).property == dto1.property
        result.get(1) == null
        result.get(2).property == dto2.property
    }

    def "Mapping a business stream to a dto stream calls the single object method correctly"() {
        given:
        TestBus bus1 = new TestBus("1")
        TestBus bus2 = new TestBus("2")
        Stream<TestBus> busStream = Stream.of(bus1, null, bus2)

        when:
        Stream<TestDto> dtoStream = mapper.mapToDto(busStream)
        List<TestDto> result = dtoStream.collect(Collectors.toList())

        then:
        result.get(0).property == bus1.property
        result.get(1) == null
        result.get(2).property == bus2.property
    }

    def "Mapping a business list to a dto list calls the stream method correctly"() {
        given:
        TestBus bus1 = new TestBus("1")
        TestBus bus2 = new TestBus("2")
        List<TestBus> busList = new ArrayList<>()
        busList.add(bus1)
        busList.add(null)
        busList.add(bus2)

        when:
        List<TestDto> result = mapper.mapToDto(busList)

        then:
        result.get(0).property == bus1.property
        result.get(1) == null
        result.get(2).property == bus2.property
    }

    /* *************** */
    /* Example classes */
    /* *************** */

    private class TestDto {
        private String property

        TestDto() {}

        TestDto(String property) {
            this.property = property
        }
    }

    private class TestBus {
        private String property

        TestBus(){}

        TestBus(String property) {
            this.property = property
        }
    }

    private class SimpleMapper implements Mapper<TestBus, TestDto> {

        @Override
        TestBus mapToBusiness(TestDto dto) {
            if (dto == null) {
                return null
            } else {
                TestBus bus = new TestBus()
                bus.property = dto.property
                return bus
            }
        }

        @Override
        TestDto mapToDto(TestBus bus) {
            if (bus == null) {
                return null
            } else {
                TestDto dto = new TestDto()
                dto.property = bus.property
                return dto
            }
        }
    }
}
