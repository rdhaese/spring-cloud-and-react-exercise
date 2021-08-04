package com.rdhaese.springcloudandreactexercise.studentservice.controller.mapper;

import com.rdhaese.springcloudandreactexercise.mapper.Mapper;
import com.rdhaese.springcloudandreactexercise.studentservice.database.model.Student;
import com.rdhaese.springcloudandreactexercise.studentservice.dto.CreateStudentDto;
import org.springframework.stereotype.Component;

@Component
public class CreateStudentMapper implements Mapper<Student, CreateStudentDto> {

    @Override
    public Student mapToBusiness(CreateStudentDto dto) {
        if (dto == null) {
            return null;
        } else {
            Student student = new Student();
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setEmail(dto.getEmail());
            student.setAddressUuid(dto.getAddressUuid());
            return student;
        }
    }

    @Override
    public CreateStudentDto mapToDto(Student bus) {
        if (bus == null) {
            return null;
        } else {
            CreateStudentDto studentDto = new CreateStudentDto();
            studentDto.setFirstName(bus.getFirstName());
            studentDto.setLastName(bus.getLastName());
            studentDto.setEmail(bus.getEmail());
            studentDto.setAddressUuid(bus.getAddressUuid());
            return studentDto;
        }
    }
}
