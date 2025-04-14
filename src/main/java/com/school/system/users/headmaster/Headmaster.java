package com.school.system.users.headmaster;

import com.school.system.school.School;
import com.school.system.users.Role;
import com.school.system.users.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Table(name = "headmasters")
public class Headmaster extends User {
    @OneToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    public Headmaster() {
        this.setRole(Role.HEADMASTER);
    }

    public Headmaster(UUID id,
                      String name,
                      String middleName,
                      String surname,
                      String nationalIdNumber,
                      String username,
                      String password,
                      String email,
                      School school) {
        super(id, name, middleName, surname, nationalIdNumber, username, password, email);
        this.school = school;
        this.setRole(Role.HEADMASTER);
    }

    public Headmaster(String name,
                      String middleName,
                      String surname,
                      String nationalIdNumber,
                      String username,
                      String password,
                      String email,
                      School school) {
        super(name, middleName, surname, nationalIdNumber, username, password, email);
        this.school = school;
        this.setRole(Role.HEADMASTER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }
}
