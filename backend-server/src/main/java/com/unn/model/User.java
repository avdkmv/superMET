package com.unn.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s_user")
@DynamicUpdate
public class User implements UserDetails {
    private static final long serialVersionUID = -2840020640197931220L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private UserType type;

    private String username;

    @JsonIgnore
    private String password;

    private String mail;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private boolean isLoggedIn;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<GrantedAuthority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Message> messages;

    public User(UserType type, String username, String password, String mail) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public User addAuthority(SimpleGrantedAuthority authority) {
        if (!this.authorities.contains(authority)) {
            this.authorities.add(authority);
        }

        return this;
    }

    public SimpleGrantedAuthority createAuthority() {
        return new SimpleGrantedAuthority(this.type.getName());
    }

    @Override
    public String toString() {
        return (
            "User [authorities=" +
            addAuthority(createAuthority()).authorities +
            ", id=" +
            id +
            ", isAccountNonExpired=" +
            isAccountNonExpired +
            ", isAccountNonLocked=" +
            isAccountNonLocked +
            ", isCredentialsNonExpired=" +
            isCredentialsNonExpired +
            ", isEnabled=" +
            isEnabled +
            ", isLoggedIn=" +
            isLoggedIn +
            ", mail=" +
            mail +
            ", password=" +
            password +
            ", type=" +
            type +
            ", username=" +
            username +
            "]"
        );
    }
}
