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
import com.unn.util.AuthUtils;
import org.springframework.core.convert.ConversionException;
import org.springframework.security.core.Authentication;
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
        Arrays
            .stream(UserTypes.values())
            .forEach(
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

    public Optional<UserType> findUserTypeById(Long id) {
        return userTypes.findById(id);
    }

    @Override
    public Optional<User> createUser(SignupRequest request) {
        Optional<UserType> userType = findUserTypeById(request.getTypeId());

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
        return userRepo.findByEmail(mail);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return userRepo.findById(id);
    }

    public Optional<User> findUser(Authentication auth) {
        if (AuthUtils.notAuthenticated(auth)) {
            return Optional.empty();
        }

        Optional<User> user = findByUsername(auth.getName());

        if (user.isPresent()) {
            user.get().addAuthority(user.get().createAuthority());
        }

        return user;
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<User> deleteUser(String mail) {
        Optional<User> user = userRepo.findByEmail(mail);
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
    public Optional<User> updateUser(User updatedUser) {
        Optional<User> existingUser = userRepo.findById(updatedUser.getId());

        existingUser.ifPresent(
            user -> {
                user.setAuthorities(updatedUser.getAuthorities());
                user.setEnabled(updatedUser.isEnabled());
                user.setAccountNonExpired(updatedUser.isAccountNonExpired());
                user.setAccountNonLocked(updatedUser.isAccountNonLocked());
                user.setCredentialsNonExpired(updatedUser.isCredentialsNonExpired());
                user.setLoggedIn(updatedUser.isLoggedIn());

                user = userRepo.save(user);
            }
        );

        return existingUser;
    }

    @Override
    public Optional<List<User>> getAllByType(UserTypes type) {
        return userRepo.findAllByTypeId(type.getId());
    }

    @Override
    public void clearTable() {
        userRepo.deleteAll();
    }

    public Optional<Doctor> getDoctor(Long id) {
        return getUserByType(id, Doctor.class);
    }

    public Optional<Patient> getPatient(Long id) {
        return getUserByType(id, Patient.class);
    }

    public Optional<Doctor> getDoctor(String username) {
        return getUserByUsernameAndType(username, Doctor.class);
    }

    public Optional<Patient> getPatient(String username) {
        return getUserByUsernameAndType(username, Patient.class);
    }

    private <T extends User> Optional<T> getUserByType(Long id, Class<T> type) {
        try {
            Optional<T> user = userRepo.findById(id, type);

            if (user.isPresent()) {
                return user;
            }
        } catch (ConversionException ex) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    private <T extends User> Optional<T> getUserByUsernameAndType(String username, Class<T> type) {
        try {
            Optional<T> user = userRepo.findByUsername(username, type);

            if (user.isPresent()) {
                return user;
            }
        } catch (ConversionException ex) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    public Optional<Doctor> attachDoctorToFacility(Long doctorId, Long facilityId) {
        Optional<Doctor> doctor = getDoctor(doctorId);
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

        return Optional.of(userRepo.save(user));
    }

    private void updateUserParams(User user, UserType type, SignupRequest request) {
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setType(type);
    }
}
