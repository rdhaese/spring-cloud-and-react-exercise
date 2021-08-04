package com.rdhaese.springcloudandreactexercise.studentservice.database.model

import spock.lang.Specification

class StudentSpecification extends Specification{

    private static final TEST_ID_1 = 1L
    private static final TEST_FIRSTNAME_1 = "Test Firstname 1"
    private static final TEST_LASTNAME_1 = "Test lastname 1"
    private static final TEST_EMAIL_1 = "test1@email.com"
    private static final TEST_ID_2 = 2L
    private static final TEST_FIRSTNAME_2 = "Test Firstname 2"
    private static final TEST_LASTNAME_2 = "Test lastname 2"
    private static final TEST_EMAIL_2 = "test2@email.com"


    def "Students with the same email addresses are equal"(){
        given:
        Student student1 = createTestStudent1()
        Student student2 = createTestStudent2()
        student2.email = TEST_EMAIL_1

        expect:
        student1 == student2
    }

    def "Students with other email addresses are not equal"(){
        given:
        Student student1 = createTestStudent1()
        Student student2 = createTestStudent1()
        student2.email = TEST_EMAIL_2

        expect:
        student1 != student2
    }

    def "Equality goes both ways"(){
        given:
        Student student1 = createTestStudent1()
        Student student2 = createTestStudent2()
        student2.email = TEST_EMAIL_1

        when:
        Boolean oneEqualsTwo = student1 == student2
        Boolean twoEqualsOne = student2 == student1

        then:
        oneEqualsTwo == twoEqualsOne
    }

    def "Equal students have the same hashcode"() {
        given:
        Student student1 = createTestStudent1()
        Student student2 = createTestStudent2()
        student2.email = TEST_EMAIL_1

        when:
        Integer hashCode1 = student1.hashCode()
        Integer hashCode2 = student2.hashCode()

        then:
        hashCode1 == hashCode2
    }

    private Student createTestStudent1() {
        Student student = new Student()
        student.id = TEST_ID_1
        student.firstName = TEST_FIRSTNAME_1
        student.lastName = TEST_LASTNAME_1
        student.email = TEST_EMAIL_1
        return student
    }

    private Student createTestStudent2() {
        Student student = new Student()
        student.id = TEST_ID_2
        student.firstName = TEST_FIRSTNAME_2
        student.lastName = TEST_LASTNAME_2
        student.email = TEST_EMAIL_2
        return student
    }
}
