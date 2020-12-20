package com.unn.service.impl;

import com.unn.constants.Constant;
import com.unn.model.Appointment;
import com.unn.model.Chat;
import com.unn.model.Document;
import com.unn.model.Facility;
import com.unn.model.Message;
import com.unn.model.User;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.DoctorRepo;
import com.unn.repository.DocumentRepo;
import com.unn.repository.FacilityRepo;
import com.unn.repository.PatientRepo;
import com.unn.repository.UserRepo;
import com.unn.service.IValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ValidationService implements IValidationService {
    private final UserRepo userRepo;
    private final DocumentRepo documentRepo;
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final FacilityRepo facilityRepo;

    @Override
    public boolean validateUserCreation(User user) {
        if (user != null) {
            return (
                user.getUserTypeId() != null &&
                isStringParamsValid(
                    Constant.USER_PARAMS_SIZE,
                    user.getUsername(),
                    user.getPassword(),
                    user.getMail()
                ) &&
                userRepo.findByMail(user.getMail()).isEmpty()
            );
        } else {
            return false;
        }
    }

    @Override
    public boolean validateUserUpdate(User user) {
        if (user != null) {
            return (
                isStringParamsValid(Constant.USER_PARAMS_SIZE, user.getUsername(), user.getPassword()) &&
                userRepo.findByMail(user.getMail()).isPresent()
            );
        } else {
            return false;
        }
    }

    @Override
    public boolean validateMessage(Message message) {
        // TODO:  implement method
        return false;
    }

    @Override
    public boolean validateDocumentCreate(Document document) {
        if (document != null) {
            return (
                isStringParamsValid(Constant.DOCUMENT_NUMBER_SIZE, document.getNumber()) &&
                isStringParamsValid(Constant.DESCRIPTION_SIZE, document.getDescription()) &&
                documentRepo.findByNumber(document.getNumber()).isEmpty()
            );
        } else {
            return false;
        }
    }

    @Override
    public boolean validateDocumentUpdate(Document document) {
        if (document != null) {
            return (
                isStringParamsValid(Constant.DOCUMENT_NUMBER_SIZE, document.getNumber()) &&
                isStringParamsValid(Constant.DESCRIPTION_SIZE, document.getDescription()) &&
                documentRepo.findById(document.getId()).isPresent()
            );
        } else {
            return false;
        }
    }

    @Override
    public boolean validateAppointmentCreation(Long doctorId) {
        return doctorRepo.findById(doctorId).isPresent();
    }

    @Override
    public boolean validateChatCreation(Chat chat) {
        return (
            doctorRepo.findById(chat.getDoctor().getId()).isPresent() &&
            patientRepo.findById(chat.getPatient().getId()).isPresent()
        );
    }

    @Override
    public boolean validateFacilityCreation(Facility facility) {
        if (facility != null) {
            return (
                isStringParamsValid(Constant.FACILITY_PARAM_SIZE, facility.getName()) &&
                isStringParamsValid(Constant.DESCRIPTION_SIZE, facility.getDescription()) &&
                facilityRepo.findByName(facility.getName()).isEmpty()
            );
        }
        return false;
    }

    private boolean isStringParamsValid(int allowedSize, String... params) {
        for (String param : params) {
            if (StringUtils.isEmpty(param) || param.length() > allowedSize) {
                return false;
            }
        }
        return true;
    }

    public boolean validateWorkTime(int startTime, int endTime) {
        if ((endTime < startTime) || (endTime < 0 || startTime < 0) || (startTime > 24 || endTime > 24)) return false;
        return true;
    }
}
