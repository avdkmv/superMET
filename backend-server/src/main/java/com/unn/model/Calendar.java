package com.unn.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_calendar")
@DynamicUpdate
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Doctor doctor;

    @OneToMany(mappedBy = "calendar")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Map<Long, Appointment> appointments;

    int startTime;
    int endTime;
}
