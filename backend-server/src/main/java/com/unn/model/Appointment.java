package com.unn.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_appointments")
@DynamicUpdate
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonProperty("patient_id")
    @JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    @JsonProperty("calendar_id")
    @JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Calendar calendar;

    private Long resultId;

    private LocalDateTime date;

    private String code;

    private boolean busy;

    public Appointment(Doctor doctor, LocalDateTime date, Calendar calendar) {
        this.doctor = doctor;
        this.date = date;
        this.busy = false;
        this.calendar = calendar;
    }
}
