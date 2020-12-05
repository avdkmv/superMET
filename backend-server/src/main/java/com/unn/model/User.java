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
import javax.validation.constraints.Size;

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

  @Size(min = 3, max = 15, message = "Username size must be 3 to 15 symbols.")
  private String username;

  @Size(min = 5, max = 65, message = "Password size must be 6 to 65 symbols.")
  private String password;

  @Size(min = 5, max = 65, message = "Mail size must be 6 to 65 symbols.")
  private String mail;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "userId",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Doctor> doctorIds;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "userId",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Patient> patientIds;

  @OneToMany(
    fetch = FetchType.EAGER,
    mappedBy = "userId",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Message> messageIds;
}
