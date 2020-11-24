package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.Chat;
import com.unn.model.Message;
import com.unn.repository.ChatRepo;
import com.unn.service.IChatService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
  private final ChatRepo chatRepo;
  
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
