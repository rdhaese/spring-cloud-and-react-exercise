package com.rdhaese.springcloudandreactexercise.studentservice.database.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.PersistenceException

@DataJpaTest
class StudentDatabaseSpecification extends Specification {

    private static final TEST_FIRSTNAME = "Test Firstname"
    private static final TEST_LASTNAME = "Test lastname"
    private static final TEST_EMAIL = "test@email.com"
    private static final TEST_UUID = "123UUID"

    @Autowired
    private EntityManager entityManager

    def "id should be generated"(){
        given:
        Student student = createTestStudent()
        student.id = null;

        when:
        entityManager.persist(student)
        entityManager.flush()

        then:
        student.id != null
    }

    def "firstName should not be null"(){
        given:
        Student student = createTestStudent()
        student.firstName = null;

        when:
        entityManager.persist(student)
        entityManager.flush()

        then:
        thrown PersistenceException
    }

    def "lastName should not be null"() {
        given:
        Student student = createTestStudent()
        student.lastName = null;

        when:
        entityManager.persist(student)
        entityManager.flush()

        then:
        thrown PersistenceException
    }

    def "email should not be null"(){
        given:
        Student student = createTestStudent()
        student.email = null;

        when:
        entityManager.persist(student)
        entityManager.flush()

        then:
        thrown PersistenceException
    }

    def "email should be unique"(){
        given:
        Student student1 = createTestStudent()
        Student student2 = createTestStudent()

        when:
        entityManager.persist(student1)
        entityManager.persist(student2)
        entityManager.flush()

        then:
        thrown PersistenceException
    }

    def "addressUuid should not be null"(){
        given:
        Student student = createTestStudent()
        student.addressUuid = null;

        when:
        entityManager.persist(student)
        entityManager.flush()

        then:
        thrown PersistenceException
    }

    private Student createTestStudent(){
        Student student = new Student()
        student.firstName = TEST_FIRSTNAME
        student.lastName = TEST_LASTNAME
        student.email = TEST_EMAIL
        student.addressUuid = TEST_UUID
        return student
    }
}
