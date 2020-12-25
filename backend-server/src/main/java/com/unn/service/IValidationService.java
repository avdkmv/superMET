package com.unn.service;

import java.util.Optional;
import com.unn.dto.SignupRequest;
import com.unn.model.Chat;
import com.unn.model.Document;
import com.unn.model.Facility;
import com.unn.model.Message;
import com.unn.model.User;
import com.unn.model.UserType;

public interface IValidationService {
    boolean validateUserCreation(Optional<UserType> type, SignupRequest req);
    boolean validateUserUpdate(User user);
    boolean validateMessage(Message message);

    public boolean validateDocumentCreate(Document document);

    public boolean validateDocumentUpdate(Document document);

    boolean validateAppointmentCreation(Long doctorId);
    boolean validateChatCreation(Chat chat);
    boolean validateFacilityCreation(Facility facility);
    boolean validateWorkTime(int startTime, int endTime);
}
