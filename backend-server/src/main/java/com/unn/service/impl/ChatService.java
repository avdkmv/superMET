package com.unn.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.unn.model.*;
import com.unn.repository.ChatRepo;
import com.unn.repository.DoctorRepo;
import com.unn.repository.PatientRepo;
import com.unn.service.IChatService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
  private final ChatRepo chatRepo;
  private final DoctorRepo doctorRepo;
  private final PatientRepo patientRepo;
  
  @Override
  public Optional<Chat> createChat(Long patientId, Long doctorId) {
    Optional<Doctor> doctor = doctorRepo.findById(doctorId);
    Optional<Patient> patient = patientRepo.findById(patientId);
    Chat chat = new Chat(doctor.get(), patient.get());
    chatRepo.save(chat);
    return Optional.of(chat);
  }

  @Override
  public Optional<Chat> findChat(Long patientId, Long doctorId) {
    Optional<Patient> patient = patientRepo.findById(patientId);
    Optional<Doctor> doctor = doctorRepo.findById(doctorId);
    return chatRepo.findByDoctorIdAndPatientId(doctor.get(), patient.get());
  }

  @Override
  public Optional<Chat> findChat(Long chatId) {
    return chatRepo.findById(chatId);
  }

  @Override
  public void deleteChat(Long chatId) {
    chatRepo.deleteById(chatId);
  }

  @Override
  public boolean newMessage(Long chatId, Message message) {
    Optional<Chat> chat = chatRepo.findById(chatId);
    if (chat.isPresent() && message != null) {
      if (chat.get().getMessageHistory() == null) {
        chat.get().setMessageHistory(new HashMap<Long, Message>());
      }
      chat.get().getMessageHistory().put(message.getId(), message);
      chatRepo.save(chat.get());
      return true;
    }
    return false;
  }
}
