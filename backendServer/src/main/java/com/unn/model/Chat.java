package com.unn.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "s_chat")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Chat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "patient_id")
  private Patient patientId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_id")
  private Doctor doctorId;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private Map<Long, Message> messageHistory;
}
