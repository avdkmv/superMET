package com.unn.service.impl;

import com.unn.constants.Constant;
import com.unn.model.*;
import com.unn.repository.AppointmentRepo;
import com.unn.repository.DoctorRepo;
import com.unn.repository.PatientRepo;
import com.unn.repository.UserRepo;
import com.unn.service.IValidationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidationService implements IValidationService {
  private final UserRepo userRepo;
  private final AppointmentRepo appointmentRepo;
  private final DoctorRepo doctorRepo;
  private final PatientRepo patientRepo;

  @Override
  public boolean validateUserCreation(User user) {
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
  }

  @Override
  public boolean validateUserUpdate(User user) {
    return (
      isStringParamsValid(
        Constant.USER_PARAMS_SIZE,
        user.getUsername(),
        user.getPassword()
      ) &&
      userRepo.findByMail(user.getMail()).isPresent()
    );
  }

  @Override
  public boolean validateMessage(Message message) {
    // TODO:  implement method
    return false;
  }

  @Override
  public boolean validateDocument(Document document) {
    // TODO:  implement method
    return false;
  }

  @Override
  public boolean validateAppointmentCreation(Appointment appointment) {
    return(
      doctorRepo.findById(appointment.getDoctor().getId()).isPresent() &&
      patientRepo.findById(appointment.getPatient().getId()).isPresent()
    );
  }

  @Override
  public boolean validateChatCreation(Chat chat) {
    return(
      doctorRepo.findById(chat.getDoctor().getId()).isPresent() &&
      patientRepo.findById(chat.getPatient().getId()).isPresent()
    );
  }

  private boolean isStringParamsValid(int allowedSize, String... params) {
    for (String param : params) {
      if (StringUtils.isEmpty(param) || param.length() > allowedSize) {
        return false;
      }
    }
    return true;
  }
}
