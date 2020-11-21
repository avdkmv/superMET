package com.unn.service.impl;

import com.unn.model.Chat;
import com.unn.model.Message;
import com.unn.service.IChatService;
import java.util.Optional;

public class ChatService implements IChatService {

  @Override
  public Optional<Chat> createChat(Long patientId, Long doctorId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Chat> findChat(Long patientId, Long doctorId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Chat> findChat(Long chatId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Chat> deleteChat(Long chatId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public void newMessage(Long chatId, Message message) {
    // TODO:  implement method

  }
}
