package com.unn.service.impl;

import com.unn.constants.Constant;
import com.unn.model.Appointment;
import com.unn.model.Document;
import com.unn.model.Message;
import com.unn.model.User;
import com.unn.repository.UserRepo;
import com.unn.service.IValidationService;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService implements IValidationService {
  
  private final UserRepo userRepo;

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
  public boolean validateAppointment(Appointment appointment) {
    // TODO:  implement method
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
}
