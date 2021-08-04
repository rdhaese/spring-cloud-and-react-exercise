package com.rdhaese.springcloudandreactexercise.studentservice.controller.mapper;

import com.rdhaese.springcloudandreactexercise.mapper.Mapper;
import com.rdhaese.springcloudandreactexercise.studentservice.database.model.Student;
import com.rdhaese.springcloudandreactexercise.studentservice.dto.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<Student, StudentDto> {

    @Override
    public Student mapToBusiness(StudentDto dto) {
        if (dto == null) {
            return null;
        } else {
            Student student = new Student();
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setEmail(dto.getEmail());
            return student;
        }
    }

    @Override
    public StudentDto mapToDto(Student bus) {
        if (bus == null) {
            return null;
        } else {
            StudentDto studentDto = new StudentDto();
            studentDto.setFirstName(bus.getFirstName());
            studentDto.setLastName(bus.getLastName());
            studentDto.setEmail(bus.getEmail());
            return studentDto;
        }
    }
}
