package com.unn.controller;

import com.unn.model.Chat;
import com.unn.model.Message;
import com.unn.service.impl.ChatService;

import com.unn.service.impl.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
  private final ChatService chatService;
  private final ValidationService validationService;

  @GetMapping("/{id}")
  public ResponseEntity<Chat> getChat(@PathVariable(name = "id") Long id) {
    Optional<Chat> chat = chatService.findChat(id);
    if (chat.isPresent()) {
      return ResponseEntity.ok(chat.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping("/chat/{doctorId}/{patientId}")
  public ResponseEntity<Chat> getChat(@PathVariable(name = "doctorId") Long doctorId,
                                      @PathVariable(name = "patientId") Long patientId) {
    Optional<Chat> chat = chatService.findChat(doctorId, patientId);
    if (chat.isPresent()) {
      return ResponseEntity.ok(chat.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Chat> deleteChat(@PathVariable(name = "id") Long id) {
    Optional<Chat> chat = chatService.deleteChat(id);
    if (chat.isPresent()) {
      return ResponseEntity.ok(chat.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Chat> createChat(@RequestBody @Valid Chat chat) {
    if (validationService.validateChatCreation(chat)) {
      chatService.createChat(chat);
      return ResponseEntity.ok(chat);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping("/chat/{id}/add_message/{message}")
  public ResponseEntity<Chat> addMessage(@PathVariable(name = "id") Long chatId,
                                         @PathVariable(name = "message") Message message) {
    Optional<Chat> chat = chatService.findChat(chatId);
    if (chat.isPresent()) {
      chatService.newMessage(chatId, message);
      return ResponseEntity.ok(chat.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

}
