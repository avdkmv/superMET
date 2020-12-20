package com.unn.service;

import com.unn.model.Chat;
import com.unn.model.Message;
import java.util.Optional;

public interface IChatService {
    Optional<Chat> createChat(Chat chat);

    Optional<Chat> findChat(Long doctorId, Long patientId);
    Optional<Chat> findChat(Long chatId);

    Optional<Chat> deleteChat(Long chatId);

    Optional<Chat> newMessage(Long chatId, Message message);
}
