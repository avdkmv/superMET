package com.unn.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "s_user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userTypeId;

  private String username;

  private String password;

  private String mail;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Doctor> doctorIds;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Patient> patientIds;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Message> messageIds;

  public User(Long userTypeId, String username, String password, String mail) {
    this.userTypeId = userTypeId;
    this.username = username;
    this.password = password;
    this.mail = mail;
  }
}
