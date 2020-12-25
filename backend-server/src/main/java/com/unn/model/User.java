package com.unn.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_user")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonManagedReference
    private UserType type;

    private String username;

    private String password;

    private String mail;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Patient> patients;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Message> messages;

    public User(UserType type, String username, String password, String mail) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }
}
