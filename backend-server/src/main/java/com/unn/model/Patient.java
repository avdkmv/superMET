package com.unn.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Patient extends User {
    private static final long serialVersionUID = -7051775815559883468L;

    @OneToMany(mappedBy = "patient")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Appointment> chats = new ArrayList<>();
}
