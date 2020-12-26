package com.unn.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import com.unn.constants.UserTypes;
import com.unn.dto.SignupRequest;
import com.unn.model.Calendar;
import com.unn.model.Doctor;
import com.unn.model.Facility;
import com.unn.model.Patient;
import com.unn.model.User;
import com.unn.model.UserType;
import com.unn.repository.FacilityRepo;
import com.unn.repository.UserRepo;
import com.unn.repository.UserTypeRepo;
import com.unn.service.IUserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserTypeRepo userTypes;
    private final UserRepo userRepo;
    private final FacilityRepo facilityRepo;

    private final ValidationService validation;
    private final CalendarService calendarService;

    @PostConstruct
    private void createUserTypes() {
        Arrays.stream(UserTypes.values()).forEach(
            type -> {
                if (!userTypes.existsById(type.getId())) {
                    UserType newType = new UserType();
                    newType.setId(type.getId());
                    newType.setName(type.toString());

                    userTypes.saveAndFlush(newType);
                }
            }
        );
    }

    @Override
    public Optional<User> createUser(SignupRequest request) {
        Optional<UserType> userType = userTypes.findById(request.getTypeId());

        if (!validation.validateUserCreation(userType, request)) {
            return Optional.empty();
        }

        User newUser = new User();
        Long userTypeId = userType.get().getId();
        if (userTypeId.equals(UserTypes.DOCTOR.getId())) {
            return saveDoctor(newUser, request, userType.get());
        } else if (userTypeId.equals(UserTypes.PATIENT.getId())) {
            return savePatient(newUser, request, userType.get());
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String mail) {
        return userRepo.findByMail(mail);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> deleteUser(String mail) {
        Optional<User> user = userRepo.findByMail(mail);
        if (user.isPresent()) {
            userRepo.deleteById(user.get().getId());
        }
        return user;
    }

    @Override
    public Optional<User> deleteUser(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            userRepo.deleteById(user.get().getId());
        }
        return user;
    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> updatedUser = userRepo.findByMail(user.getMail());
        updatedUser.get().setUsername(user.getUsername());
        updatedUser.get().setPassword(user.getPassword());

        userRepo.save(updatedUser.get());
        return updatedUser;
    }

    @Override
    public Optional<List<User>> getAllByType(UserTypes type) {
        return userRepo.findAllByTypeId(type.getId());
    }

    @Override
    public void clearUserTable() {
        userRepo.deleteAll();
    }

    public Optional<Doctor> getDoctor(Long id) {
        return getUserByType(id, Doctor.class);
    }

    public Optional<Patient> getPatient(Long id) {
        return getUserByType(id, Patient.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends User> Optional<T> getUserByType(Long id, Class<T> type) {
        Optional<User> user = findUser(id);

        if (user.isPresent()) {
            return Optional.of((T) user.get());
        }

        return Optional.empty();
    }

    public Optional<Doctor> attachDoctorToFacility(Long doctorId, Long facilityId) {
        Optional<Doctor> doctor = getUserByType(doctorId, Doctor.class);
        Optional<Facility> facility = facilityRepo.findById(facilityId);

        if (!doctor.isPresent() || !facility.isPresent()) {
            return Optional.empty();
        }

        doctor.get().setFacility(facility.get());
        userRepo.save(doctor.get());

        return doctor;
    }

    private Optional<User> saveDoctor(User user, SignupRequest request, UserType type) {
        user = new Doctor();
        updateUserParams(user, type, request);
        ((Doctor) user).setUser(user);

        User savedUser = userRepo.save(user);
        Optional<Calendar> calendar = calendarService.createCalendarByDoctorId((Doctor) savedUser, 10, 18);
        if (calendar.isPresent()) {
            ((Doctor) user).setCalendar(calendar.get());
        }

        return Optional.of(userRepo.save(savedUser));
    }

    private Optional<User> savePatient(User user, SignupRequest request, UserType type) {
        user = new Patient();
        updateUserParams(user, type, request);
        ((Patient) user).setUser(user);

        return Optional.of(userRepo.save(user));
    }

    private void updateUserParams(User user, UserType type, SignupRequest request) {
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setMail(request.getEmail());
        user.setType(type);
    }
}
