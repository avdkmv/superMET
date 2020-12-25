package com.unn.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonManagedReference
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    @JsonManagedReference
    private Calendar calendar;

    private Long resultId;

    private Date date;

    private String code;

    private boolean busy;

    public Appointment(Doctor doctor, Date date, Calendar calendar) {
        this.doctor = doctor;
        this.date = date;
        this.busy = false;
        this.calendar = calendar;
    }
}
