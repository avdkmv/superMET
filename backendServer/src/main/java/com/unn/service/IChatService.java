package com.unn.service;

import java.util.Optional;

import com.unn.model.Chat;
import com.unn.model.Message;

public interface IChatService {
  Optional<Chat> createChat(Long patientId, Long doctorId);

  Optional<Chat> findChat(Long patientId, Long doctorId);
  Optional<Chat> findChat(Long chatId);

  Optional<Chat> deleteChat(Long chatId);

  void newMessage(Long chatId, Message message);
}
