package com.unn.service.impl;

import com.unn.model.Appointment;
import com.unn.model.Document;
import com.unn.model.Message;
import com.unn.model.User;
import com.unn.service.IValidationService;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService implements IValidationService {
  private final int USER_PARAMS_SIZE = 127;

  /**
   * will be updated
   */

  @Override
  public boolean validateUser(User user) {
    return (
      user.getUserTypeId() != null &&
      isStringParamsValid(
        USER_PARAMS_SIZE,
        user.getUsername(),
        user.getPassword(),
        user.getMail()
      )
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
    boolean check = true;
    for (String param : params) {
      if (StringUtils.isEmpty(param) || param.length() > allowedSize) {
        check = false;
        break;
      }
    }
    return check;
  }
}
