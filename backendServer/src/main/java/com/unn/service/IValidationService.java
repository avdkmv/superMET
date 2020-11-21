package com.unn.service;

import com.unn.model.Appointment;
import com.unn.model.Document;
import com.unn.model.Message;
import com.unn.model.User;

public interface IValidationService {
  boolean validateUser(User user);
  boolean validateMessage(Message message);
  boolean validateDocument(Document document);
  boolean validateAppointment(Appointment appointment);
}
