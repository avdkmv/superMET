package com.unn.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "s_doctor")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Doctor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User userId;

  @OneToOne(mappedBy = "doctorId")
  @NotFound(action=NotFoundAction.IGNORE)
  private Calendar calendarId;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private List<Long> documentId;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "doctorId",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Appointment> appointmentIds;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "doctorId",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Appointment> chatIds;
}
