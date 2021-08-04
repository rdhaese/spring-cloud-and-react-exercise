package com.rdhaese.springcloudandreactexercise.studentservice.controller;

import com.rdhaese.springcloudandreactexercise.mapper.Mapper;
import com.rdhaese.springcloudandreactexercise.studentservice.database.model.Student;
import com.rdhaese.springcloudandreactexercise.studentservice.database.repository.StudentRepository;
import com.rdhaese.springcloudandreactexercise.studentservice.dto.AddressDto;
import com.rdhaese.springcloudandreactexercise.studentservice.dto.CreateStudentDto;
import com.rdhaese.springcloudandreactexercise.studentservice.dto.StudentDto;
import com.rdhaese.springcloudandreactexercise.studentservice.proxy.AddressServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    private static final String URL_ROOT = "/student";
    private static final String URL_BY_EMAIL = URL_ROOT + "/{email}";

    private StudentRepository studentRepository;
    private Mapper<Student, StudentDto> studentMapper;
    private Mapper<Student, CreateStudentDto> createStudentMapper;

    private AddressServiceProxy addressServiceProxy;

    public StudentController(
            @Autowired StudentRepository studentRepository,
            @Autowired Mapper<Student, StudentDto> studentMapper,
            @Autowired Mapper<Student, CreateStudentDto> createStudentMapper,
            @Autowired AddressServiceProxy addressServiceProxy) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.createStudentMapper = createStudentMapper;
        this.addressServiceProxy = addressServiceProxy;
    }

    @GetMapping(value = URL_ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> getStudents(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "3") Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> studentsPage = studentRepository.findAll(pageable);
        List<StudentDto> studentDtos = studentMapper.mapToDto(studentsPage.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("students",studentDtos);
        response.put("currentPage", studentsPage.getNumber());
        response.put("totalItems", studentsPage.getTotalElements());
        response.put("totalPages", studentsPage.getTotalPages());

        return response;
    }

    @GetMapping(value = URL_BY_EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    StudentDto getStudent(@PathVariable String email,
                          @RequestParam(required = false, defaultValue = "true") Boolean includeAddress) {
        Student student = studentRepository.getByEmail(email);

        if (student == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Student with email [%s] does not exist.", email)
            );
        } else {
            StudentDto studentDto = studentMapper.mapToDto(student);
            if (includeAddress) {
                AddressDto addressDto = addressServiceProxy.getAddress(student.getAddressUuid());
                studentDto.setAddressDto(addressDto);
            }
            return studentDto;
        }
    }

    @PostMapping(value = URL_ROOT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    StudentDto createStudent(
            @Valid @RequestBody CreateStudentDto createStudentDto,
            @RequestParam(required = false, defaultValue = "false") Boolean includeAddressInResponse
    ) {
        if (studentRepository.getByEmail(createStudentDto.getEmail()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Student with email [%s] already exists.", createStudentDto.getEmail())
            );
        } else {
            Student student = createStudentMapper.mapToBusiness(createStudentDto);
            Student savedStudent = studentRepository.save(student);
            StudentDto studentDto = studentMapper.mapToDto(savedStudent);
            if (includeAddressInResponse) {
                AddressDto addressDto = addressServiceProxy.getAddress(student.getAddressUuid());
                studentDto.setAddressDto(addressDto);
            }
            return studentDto;
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(
                errors.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
