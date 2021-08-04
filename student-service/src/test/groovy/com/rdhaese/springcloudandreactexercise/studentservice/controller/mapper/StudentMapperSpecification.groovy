package com.rdhaese.springcloudandreactexercise.studentservice.controller.mapper

import com.rdhaese.springcloudandreactexercise.studentservice.database.model.Student
import com.rdhaese.springcloudandreactexercise.studentservice.dto.StudentDto
import spock.lang.Specification

class StudentMapperSpecification extends Specification {

    private static final TEST_FIRSTNAME = "Test Firstname"
    private static final TEST_LASTNAME = "Test lastname"
    private static final TEST_EMAIL = "test@email.com"

    private static final STUDENT_MAPPER = new StudentMapper()

    def "Dto -> Business: All necessary properties are mapped"() {
        given:
        StudentDto dto = createTestDto()

        when:
        Student bus = STUDENT_MAPPER.mapToBusiness(dto)

        then:
        bus.firstName == TEST_FIRSTNAME
        bus.lastName == TEST_LASTNAME
        bus.email == TEST_EMAIL
    }

    def "Dto -> Business: id property stays null"() {
        given:
        StudentDto dto = createTestDto()

        when:
        Student bus = STUDENT_MAPPER.mapToBusiness(dto)

        then:
        bus.id == null
    }

    def "Dto -> Business: passing null returns null"(){
        given:
        StudentDto dto = null

        when:
        Student bus = STUDENT_MAPPER.mapToBusiness(dto)

        then:
        bus == null
    }

    def "Business -> Dto: All necessary properties are mapped"() {
        given:
        Student bus = createTestBus()

        when:
        StudentDto dto = STUDENT_MAPPER.mapToDto(bus)

        then:
        dto.firstName == TEST_FIRSTNAME
        dto.lastName == TEST_LASTNAME
        dto.email == TEST_EMAIL
    }

    def "Business -> Dto: passing null returns null"(){
        given:
        Student bus = null

        when:
        StudentDto dto = STUDENT_MAPPER.mapToDto(bus)

        then:
        dto == null
    }

    private StudentDto createTestDto() {
        StudentDto dto = new StudentDto();
        dto.firstName = TEST_FIRSTNAME
        dto.lastName = TEST_LASTNAME
        dto.email = TEST_EMAIL
        return dto
    }

    private Student createTestBus(){
        Student bus = new Student();
        bus.firstName = TEST_FIRSTNAME
        bus.lastName = TEST_LASTNAME
        bus.email = TEST_EMAIL
        return bus
    }
}
