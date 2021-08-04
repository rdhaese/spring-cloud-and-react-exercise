package com.rdhaese.springcloudandreactexercise.studentservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.rdhaese.springcloudandreactexercise.mapper.Mapper
import com.rdhaese.springcloudandreactexercise.studentservice.database.model.Student
import com.rdhaese.springcloudandreactexercise.studentservice.database.repository.StudentRepository
import com.rdhaese.springcloudandreactexercise.studentservice.dto.AddressDto
import com.rdhaese.springcloudandreactexercise.studentservice.dto.CreateStudentDto
import com.rdhaese.springcloudandreactexercise.studentservice.dto.StudentDto
import com.rdhaese.springcloudandreactexercise.studentservice.proxy.AddressServiceProxy
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//TODO Some tests for including address in response still missing

@WebMvcTest(controllers = [StudentController])
class StudentControllerSpecification extends Specification {

    private static final URL_ROOT = "/student"
    private static final TEST_ID_1 = 1L
    private static final TEST_FIRSTNAME_1 = "Test Firstname 1"
    private static final TEST_LASTNAME_1 = "Test lastname 1"
    private static final TEST_EMAIL_1 = "test1@email.com"
    private static final TEST_ADDRESS_UUID_1 = "123UUID"
    private static final TEST_ADDRESS_STREET_1 = "Street1"
    private static final TEST_ADDRESS_NUMBER_1 = 1
    private static final TEST_ADDRESS_NUMBER_EXTRA_1 = null
    private static final TEST_ADDRESS_CITY_1 = "City1"
    private static final TEST_ADDRESS_POSTAL_CODE_1 = "postalCode1"
    private static final TEST_ID_2 = 2L
    private static final TEST_FIRSTNAME_2 = "Test Firstname 2"
    private static final TEST_LASTNAME_2 = "Test lastname 2"
    private static final TEST_EMAIL_2 = "test2@email.com"
    private static final TEST_ADDRESS_UUID_2 = "456UUID"
    private static final TEST_ADDRESS_STREET_2 = "Street2"
    private static final TEST_ADDRESS_NUMBER_2 = 2
    private static final TEST_ADDRESS_NUMBER_EXTRA_2 = "B"
    private static final TEST_ADDRESS_CITY_2 = "City2"
    private static final TEST_ADDRESS_POSTAL_CODE_2 = "postalCode2"

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @SpringBean
    private Mapper<Student, StudentDto> studentMapper = Mock()

    @SpringBean
    private Mapper<Student, CreateStudentDto> createStudentMapper = Mock()

    @SpringBean
    private StudentRepository studentRepository = Mock()

    @SpringBean
    private AddressServiceProxy addressServiceProxy = Mock()

    def "GET '/student' returns 3 students, first page, with default page parameters"() {
        given:
        List<StudentDto> studentDtos = List.of(createTestStudentDto1(), createTestStudentDto2(), createTestStudentDto1())
        and:
        Pageable pageable = PageRequest.of(0, 3)
        List<Student> studentsInPage = List.of(createTestStudent1(), createTestStudent2(), createTestStudent1())
        Page<Student> studentsPage = new PageImpl(studentsInPage, pageable, 4)

        when:
        def results = mockMvc.perform(get(URL_ROOT))

        then:
        1 * studentRepository.findAll(pageable) >> studentsPage
        1 * studentMapper.mapToDto(studentsInPage) >> studentDtos
        and:
        results.andExpect(status().isOk())
                .andExpect(jsonPath("\$.totalItems").value(4))
                .andExpect(jsonPath("\$.totalPages").value(2))
                .andExpect(jsonPath("\$.currentPage").value(0))
                .andExpect(jsonPath("\$.students.length()").value(3))
                .andExpect(jsonPath("\$.students[0].firstName").value(TEST_FIRSTNAME_1))
                .andExpect(jsonPath("\$.students[0].lastName").value(TEST_LASTNAME_1))
                .andExpect(jsonPath("\$.students[0].email").value(TEST_EMAIL_1))
                .andExpect(jsonPath("\$.students[0].address").value(null))
                .andExpect(jsonPath("\$.students[1].firstName").value(TEST_FIRSTNAME_2))
                .andExpect(jsonPath("\$.students[1].lastName").value(TEST_LASTNAME_2))
                .andExpect(jsonPath("\$.students[1].email").value(TEST_EMAIL_2))
                .andExpect(jsonPath("\$.students[1].address").value(null))
                .andExpect(jsonPath("\$.students[2].firstName").value(TEST_FIRSTNAME_1))
    }

    def "GET '/student' with non default page parameters"() {
        given:
        List<StudentDto> studentDtos = List.of(createTestStudentDto1(), createTestStudentDto2())
        and:
        Pageable pageable = PageRequest.of(1, 2)
        List<Student> studentsInPage = List.of(createTestStudent1(), createTestStudent2())
        Page<Student> studentsPage = new PageImpl(studentsInPage, pageable, 4)

        when:
        def results = mockMvc.perform(get("$URL_ROOT?page=1&size=2"))

        then:
        1 * studentRepository.findAll(pageable) >> studentsPage
        1 * studentMapper.mapToDto(studentsInPage) >> studentDtos
        and:
        results.andExpect(status().isOk())
                .andExpect(jsonPath("\$.totalItems").value(4))
                .andExpect(jsonPath("\$.totalPages").value(2))
                .andExpect(jsonPath("\$.currentPage").value(1))
                .andExpect(jsonPath("\$.students.length()").value(2))
                .andExpect(jsonPath("\$.students[0].firstName").value(TEST_FIRSTNAME_1))
                .andExpect(jsonPath("\$.students[0].lastName").value(TEST_LASTNAME_1))
                .andExpect(jsonPath("\$.students[0].email").value(TEST_EMAIL_1))
                .andExpect(jsonPath("\$.students[0].address").value(null))
                .andExpect(jsonPath("\$.students[1].firstName").value(TEST_FIRSTNAME_2))
                .andExpect(jsonPath("\$.students[1].lastName").value(TEST_LASTNAME_2))
                .andExpect(jsonPath("\$.students[1].email").value(TEST_EMAIL_2))
                .andExpect(jsonPath("\$.students[1].address").value(null))
    }

    def "GET '/student' when none are present, an empty list is returned"() {
        given:
        Pageable pageable = PageRequest.of(0, 3)
        Page<Student> studentsPage = Page.empty(pageable)
        List<StudentDto> studentDtos = new ArrayList<>()

        when:
        def results = mockMvc.perform(get(URL_ROOT))

        then:
        1 * studentRepository.findAll(pageable) >> studentsPage
        1 * studentMapper.mapToDto(studentsPage.getContent()) >> studentDtos
        and:
        results.andExpect(status().isOk())
                .andExpect(jsonPath("\$.totalItems").value(0))
                .andExpect(jsonPath("\$.totalPages").value(0))
                .andExpect(jsonPath("\$.currentPage").value(0))
                .andExpect(jsonPath("\$.students.length()").value(0))
    }

    def "GET '/student/{email}' should return the matching student when found"() {
        given:
        Student student = createTestStudent1()
        StudentDto studentDto = createTestStudentDto1()

        when:
        def results = mockMvc.perform(get("$URL_ROOT/$TEST_EMAIL_1"))

        then:
        1 * studentRepository.getByEmail(TEST_EMAIL_1) >> student
        1 * studentMapper.mapToDto(student) >> studentDto
        and:
        results.andExpect(status().isOk())
                .andExpect(jsonPath("\$.firstName").value(TEST_FIRSTNAME_1))
                .andExpect(jsonPath("\$.lastName").value(TEST_LASTNAME_1))
                .andExpect(jsonPath("\$.email").value(TEST_EMAIL_1))
                .andExpect(jsonPath("\$.address").value(null))
    }

    def "GET '/student/{email}' should return HTTP 404 NOT FOUND when no match is found"() {
        when:
        def results = mockMvc.perform(get("$URL_ROOT/$TEST_EMAIL_1"))

        then:
        1 * studentRepository.getByEmail(TEST_EMAIL_1) >> null
        0 * studentMapper.mapToDto()
        and:
        results.andExpect(status().isNotFound())
                .andExpect(status().reason("Student with email [$TEST_EMAIL_1] does not exist."))
    }

    def "POST '/student' creates a student and returns HTTP 201 CREATED if the request is valid and the user does not yet exist"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        StudentDto studentDto = createTestStudentDto1()
        Student student = createTestStudent1()
        student.id = null
        Student createdStudent = createTestStudent1()

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        1 * studentRepository.getByEmail(TEST_EMAIL_1) >> null
        1 * createStudentMapper.mapToBusiness(createStudentDto) >> student
        1 * studentRepository.save(student) >> createdStudent
        1 * studentMapper.mapToDto(createdStudent) >> studentDto
        0 * addressServiceProxy.getAddress(_)

        and:
        result.andExpect(status().isCreated())
        and:
        with(objectMapper.readValue(result.andReturn().response.contentAsString, Map)) {
            it.firstName == TEST_FIRSTNAME_1
            it.lastName == TEST_LASTNAME_1
            it.email == TEST_EMAIL_1
            it.address == null
        }
    }

    def "POST '/student' successful, with address returned in response"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        StudentDto studentDto = createTestStudentDto1()
        Student student = createTestStudent1()
        student.id = null
        Student createdStudent = createTestStudent1()
        AddressDto addressDto = createAddressDto1()

        when:
        def result = mockMvc.perform(post(URL_ROOT + "?includeAddressInResponse=true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        1 * studentRepository.getByEmail(TEST_EMAIL_1) >> null
        1 * createStudentMapper.mapToBusiness(createStudentDto) >> student
        1 * studentRepository.save(student) >> createdStudent
        1 * studentMapper.mapToDto(createdStudent) >> studentDto
        1 * addressServiceProxy.getAddress(TEST_ADDRESS_UUID_1) >> addressDto

        and:
        result.andExpect(status().isCreated())
        and:
        with(objectMapper.readValue(result.andReturn().response.contentAsString, Map)) {
            it.firstName == TEST_FIRSTNAME_1
            it.lastName == TEST_LASTNAME_1
            it.email == TEST_EMAIL_1
            it.address.uuid == TEST_ADDRESS_UUID_1
            it.address.street == TEST_ADDRESS_STREET_1
            it.address.number == TEST_ADDRESS_NUMBER_1
            it.address.numberExtra == TEST_ADDRESS_NUMBER_EXTRA_1
            it.address.city == TEST_ADDRESS_CITY_1
            it.address.postalCode == TEST_ADDRESS_POSTAL_CODE_1
        }
    }

    def "POST '/student' with wrong MediaType should return HTTP 415 UNSUPPORTED MEDIA TYPE"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_XML)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        0 * studentRepository.getByEmail(_)
        0 * createStudentMapper.mapToBusiness(_)
        0 * studentRepository.save(_)
        0 * studentMapper.mapToDto(_)
        0 * addressServiceProxy.getAddress(_)

        and:
        result.andExpect(status().isUnsupportedMediaType())
    }

    def "POST '/student' returns HTTP 422 UNPROCESSABLE ENTITY when a student with given email already exists"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        Student student = createTestStudent1()

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        1 * studentRepository.getByEmail(TEST_EMAIL_1) >> student //TODO fix
        0 * studentRepository.getByEmail(_)
        0 * createStudentMapper.mapToBusiness(_)
        0 * studentRepository.save(_)
        0 * studentMapper.mapToDto(_)
        0 * addressServiceProxy.getAddress(_)

        and:
        result.andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("Student with email [$TEST_EMAIL_1] already exists."))
    }

    def "POST '/student' with blank firstName [#firstName] should return HTTP 400 BAD REQUEST"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        createStudentDto.firstName = firstName

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        result.andExpect(status().isBadRequest())
        result.andReturn().response.contentAsString == "[firstName: must not be blank]"

        where:
        firstName << ["", " ", null]
    }

    def "POST '/student' with blank lastName [#lastName] should return HTTP 400 BAD REQUEST"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        createStudentDto.lastName = lastName

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        result.andExpect(status().isBadRequest())
        result.andReturn().response.contentAsString == "[lastName: must not be blank]"

        where:
        lastName << ["", " ", null]
    }

    def "POST '/student' with blank email [#email] should return HTTP 400 BAD REQUEST"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        createStudentDto.email = email

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        result.andExpect(status().isBadRequest())
        //Validation order seems not fixed, so sometimes a 'must be a well-formed email address' is included,
        // before or after the expected 'blank' message
        // Test using contains
        result.andReturn().response.contentAsString.contains("email: must not be blank")

        where:
        email << ["", " ", null]
    }

    def "POST '/student' with invalid email should return HTTP 400 BAD REQUEST"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        //We trust that the @Valid and @Email annotations know how to validate an email address
        // So we do not need to test all different invalid combinations
        // We only want to check how our code reacts when an invalid email is given
        createStudentDto.email = "example.com"

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        result.andExpect(status().isBadRequest())
        result.andReturn().response.contentAsString == "[email: must be a well-formed email address]"
    }

    def "POST '/student' with blank addressUuid [#addressUuid] should return HTTP 400 BAD REQUEST"() {
        given:
        CreateStudentDto createStudentDto = createCreateTestStudentDto1()
        createStudentDto.addressUuid = addressUuid

        when:
        def result = mockMvc.perform(post(URL_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStudentDto)))

        then:
        result.andExpect(status().isBadRequest())
        //Validation order seems not fixed, so sometimes a 'must be a well-formed email address' is included,
        // before or after the expected 'blank' message
        // Test using contains
        result.andReturn().response.contentAsString.contains("addressUuid: must not be blank")

        where:
        addressUuid << ["", " ", null]
    }

    private Student createTestStudent1() {
        Student student = new Student()
        student.id = TEST_ID_1
        student.firstName = TEST_FIRSTNAME_1
        student.lastName = TEST_LASTNAME_1
        student.email = TEST_EMAIL_1
        student.addressUuid = TEST_ADDRESS_UUID_1
        return student
    }

    private Student createTestStudent2() {
        Student student = new Student()
        student.id = TEST_ID_2
        student.firstName = TEST_FIRSTNAME_2
        student.lastName = TEST_LASTNAME_2
        student.email = TEST_EMAIL_2
        student.addressUuid = TEST_ADDRESS_UUID_2
        return student
    }

    private StudentDto createTestStudentDto1() {
        StudentDto studentDto = new StudentDto()
        studentDto.firstName = TEST_FIRSTNAME_1
        studentDto.lastName = TEST_LASTNAME_1
        studentDto.email = TEST_EMAIL_1
        return studentDto
    }

    private StudentDto createTestStudentDto2() {
        StudentDto studentDto = new StudentDto()
        studentDto.firstName = TEST_FIRSTNAME_2
        studentDto.lastName = TEST_LASTNAME_2
        studentDto.email = TEST_EMAIL_2
        return studentDto
    }

    private CreateStudentDto createCreateTestStudentDto1() {
        CreateStudentDto studentDto = new CreateStudentDto()
        studentDto.firstName = TEST_FIRSTNAME_1
        studentDto.lastName = TEST_LASTNAME_1
        studentDto.email = TEST_EMAIL_1
        studentDto.addressUuid = TEST_ADDRESS_UUID_1
        return studentDto
    }

    private CreateStudentDto createCreateTestStudentDto2() {
        CreateStudentDto studentDto = new CreateStudentDto()
        studentDto.firstName = TEST_FIRSTNAME_2
        studentDto.lastName = TEST_LASTNAME_2
        studentDto.email = TEST_EMAIL_2
        studentDto.addressUuid = TEST_ADDRESS_UUID_2
        return studentDto
    }

    private AddressDto createAddressDto1(){
        AddressDto addressDto = new AddressDto()
        addressDto.uuid = TEST_ADDRESS_UUID_1
        addressDto.street = TEST_ADDRESS_STREET_1
        addressDto.number = TEST_ADDRESS_NUMBER_1
        addressDto.numberExtra = TEST_ADDRESS_NUMBER_EXTRA_1
        addressDto.city = TEST_ADDRESS_CITY_1
        addressDto.postalCode = TEST_ADDRESS_POSTAL_CODE_1
        return addressDto
    }

    private AddressDto createAddressDto2(){
        AddressDto addressDto = new AddressDto()
        addressDto.uuid = TEST_ADDRESS_UUID_2
        addressDto.street = TEST_ADDRESS_STREET_2
        addressDto.number = TEST_ADDRESS_NUMBER_2
        addressDto.numberExtra = TEST_ADDRESS_NUMBER_EXTRA_2
        addressDto.city = TEST_ADDRESS_CITY_2
        addressDto.postalCode = TEST_ADDRESS_POSTAL_CODE_2
        return addressDto
    }

}
