package com.unn.service;

import java.util.Optional;

import com.unn.model.Chat;
import com.unn.model.Message;

public interface IChatService {
  Optional<Chat> createChat(Long patientId, Long doctorId);

  Optional<Chat> findChat(Long patientId, Long doctorId);
  Optional<Chat> findChat(Long chatId);

  void deleteChat(Long chatId);

  boolean newMessage(Long chatId, Message message);
}
