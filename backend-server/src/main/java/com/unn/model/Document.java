package com.unn.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "s_document")
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String number;

  private String description;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "resource_id", referencedColumnName = "id")
  private Resource resource;

  public Document(
    String number,
    String description,
    Resource resource
  ) {
    this.number = number;
    this.description = description;
    this.resource = resource;
  }
}
