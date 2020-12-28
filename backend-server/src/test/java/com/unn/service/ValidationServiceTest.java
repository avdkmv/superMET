package com.unn.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.Chat;
import com.unn.model.Doctor;
import com.unn.model.Document;
import com.unn.model.Facility;
import com.unn.model.Patient;
import com.unn.model.User;
import com.unn.repository.DoctorRepo;
import com.unn.repository.PatientRepo;
import com.unn.repository.UserTypeRepo;
import com.unn.service.impl.AppointmentService;
import com.unn.service.impl.ChatService;
import com.unn.service.impl.DocumentService;
import com.unn.service.impl.UserService;
import com.unn.service.impl.ValidationService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationServiceTest {
    @Autowired
    private ChatService chatService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    private final SignupRequest VALID_REQUEST = new SignupRequest(
        UserTypes.PATIENT.getId(),
        "patient_username",
        "password",
        "patient_email"
    );
    private final SignupRequest VALID_DOCTOR_REQUEST = new SignupRequest(
        UserTypes.DOCTOR.getId(),
        "doctor_username",
        "password",
        "doctor_email"
    );
    private final Facility VALID_FACILITY = new Facility("facility", "description");
    private final Document VALID_DOCUMENT = new Document("12345", "description");
    private final String OUT_OF_RANGE_USERNAME =
        "so loooooooooooooooooooooooooooooooo" +
        "oooooooooooooooooooooooooooooooooooo" +
        "oooooooooooooooooooooooooooooooooooo" +
        "ooooooooooooooooooooooooong username";

    @Before
    @After
    public void clear() {
        userService.clearTable();
        documentService.clearTable();
        appointmentService.clearTable();
        chatService.clearTable();
    }

    @Test
    public void validUserCreationTest() {
        assertTrue(
            validationService.validateUserCreation(userTypeRepo.findById(UserTypes.PATIENT.getId()), VALID_REQUEST)
        );
    }

    @Test
    public void unvalidUserCreationTest() {
        assertFalse(validationService.validateUserCreation(Optional.empty(), VALID_REQUEST));
    }

    @Test
    public void validUserUpdateTest() {
        User user = userService.createUser(VALID_REQUEST).get();
        user.setUsername("nameuser");
        assertTrue(validationService.validateUserUpdate(user));
    }

    @Test
    public void unvalidUserUpdateTest() {
        User user = userService.createUser(VALID_REQUEST).get();
        user.setUsername(OUT_OF_RANGE_USERNAME);
        assertFalse(validationService.validateUserUpdate(user));
    }

    @Test
    public void validDocumentCreationTest() {
        assertTrue(validationService.validateDocumentCreate(VALID_DOCUMENT));
    }

    @Test
    public void unvalidDocumentCreationTest() {
        assertFalse(validationService.validateDocumentCreate(new Document()));
    }

    @Test
    public void validDocumentUpdateTest() {
        Document document = documentService.createDocument(VALID_DOCUMENT, getMultipartFile()).get();
        document.setNumber("54321");
        assertTrue(validationService.validateDocumentUpdate(document));
    }

    @Test
    public void unvalidDocumentUpdateTest() {
        Document document = documentService.createDocument(VALID_DOCUMENT, getMultipartFile()).get();
        document.setNumber(OUT_OF_RANGE_USERNAME);
        assertFalse(validationService.validateDocumentUpdate(document));
    }

    @Test
    public void validAppointmentCreationTest() {
        User user = userService.createUser(VALID_DOCTOR_REQUEST).get();
        Doctor doctor = doctorRepo.findById(user.getId()).get();
        assertTrue(validationService.validateAppointmentCreation(doctor.getId()));
    }

    @Test
    public void unvalidAppointmentCreationTest() {
        User user = userService.createUser(VALID_DOCTOR_REQUEST).get();
        Doctor doctor = doctorRepo.findById(user.getId()).get();
        userService.deleteUser(doctor.getId());
        assertFalse(validationService.validateAppointmentCreation(doctor.getId()));
    }

    @Test
    public void validChatCreationTest() {
        User userDoc = userService.createUser(VALID_DOCTOR_REQUEST).get();
        User userPac = userService.createUser(VALID_REQUEST).get();
        Doctor doctor = doctorRepo.findById(userDoc.getId()).get();
        Patient patient = patientRepo.findById(userPac.getId()).get();
        assertTrue(validationService.validateChatCreation(new Chat(doctor, patient)));
    }

    @Test
    public void unvalidChatCreationTest() {
        User userDoc = userService.createUser(VALID_DOCTOR_REQUEST).get();
        User userDPac = userService.createUser(VALID_REQUEST).get();
        Doctor doctor = doctorRepo.findById(userDoc.getId()).get();
        Patient patient = patientRepo.findById(userDPac.getId()).get();
        userService.deleteUser(doctor.getId());
        userService.deleteUser(patient.getId());
        assertFalse(validationService.validateChatCreation(new Chat(doctor, patient)));
    }

    @Test
    public void validFacilityCreationTest() {
        assertTrue(validationService.validateFacilityCreation(VALID_FACILITY));
    }

    @Test
    public void unvalidFacilityCreationTest() {
        Facility facility = VALID_FACILITY;
        facility.setName(OUT_OF_RANGE_USERNAME);
        assertFalse(validationService.validateFacilityCreation(facility));
    }

    private MultipartFile getMultipartFile() {
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }
}
