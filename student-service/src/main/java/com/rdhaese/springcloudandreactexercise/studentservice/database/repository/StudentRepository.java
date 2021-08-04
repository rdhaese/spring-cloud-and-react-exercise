package com.rdhaese.springcloudandreactexercise.studentservice.database.repository;

import com.rdhaese.springcloudandreactexercise.studentservice.database.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Student getByEmail(String email);
}
