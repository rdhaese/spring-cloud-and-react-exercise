package com.rdhaese.springcloudandreactexercise.studentservice.database.repository


import com.rdhaese.springcloudandreactexercise.studentservice.database.model.Student
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class StudentRepositorySpecification extends Specification{

    private static final TEST_FIRSTNAME = "Test Firstname"
    private static final TEST_LASTNAME = "Test lastname"
    private static final TEST_EMAIL = "test@email.com"
    private static final TEST_OTHER_EMAIL = "other@email.com"
    private static final TEST_UUID = "123UUID"

    private Student testStudent

    @Autowired
    private StudentRepository studentRepository;

    def setup(){
        testStudent = createTestStudent()
        studentRepository.save(testStudent)
    }

    def "getByEmail found returns matching student"(){
        when:
        Student found = studentRepository.getByEmail(TEST_EMAIL)

        then:
        testStudent == found
    }

    def "getByEmail not found returns null"(){
        when:
        Student found = studentRepository.getByEmail(TEST_OTHER_EMAIL)

        then:
        found == null
    }

    private Student createTestStudent() {
        Student student = new Student()
        student.firstName = TEST_FIRSTNAME
        student.lastName = TEST_LASTNAME
        student.email = TEST_EMAIL
        student.addressUuid = TEST_UUID
        return student
    }
}
