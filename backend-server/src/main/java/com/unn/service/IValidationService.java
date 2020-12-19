package com.unn.service;

import com.unn.model.*;

public interface IValidationService {
  boolean validateUserCreation(User user);
  boolean validateUserUpdate(User user);
  boolean validateMessage(Message message);
  public boolean validateDocumentCreate(Document document);
  public boolean validateDocumentUpdate(Document document);
  boolean validateAppointmentCreation(Appointment appointment);
  boolean validateChatCreation(Chat chat);
}
