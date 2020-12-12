package com.unn.service;

import com.unn.model.Appointment;
import com.unn.model.Document;
import com.unn.model.Message;
import com.unn.model.User;

public interface IValidationService {
  boolean validateUserCreation(User user);
  boolean validateUserUpdate(User user);
  boolean validateMessage(Message message);
  public boolean validateDocumentCreate(Document document);
  public boolean validateDocumentUpdate(Document document);
  boolean validateAppointment(Appointment appointment);
}
