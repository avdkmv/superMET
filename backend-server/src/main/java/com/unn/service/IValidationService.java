package com.unn.service;

import com.unn.model.*;

public interface IValidationService {
  boolean validateUserCreation(User user);
  boolean validateUserUpdate(User user);
  boolean validateMessage(Message message);
  boolean validateDocument(Document document);
  boolean validateAppointmentCreation(Appointment appointment);
  boolean validateChatCreation(Chat chat);
}
