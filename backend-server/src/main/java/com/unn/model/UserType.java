package com.unn.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_usertype")
@DynamicUpdate
public class UserType {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "type")
    @JsonBackReference
    private List<User> users;
}
