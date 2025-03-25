package com.school.system.users.teacher;

import com.school.system.users.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher extends User {
//    @Column(nullable = false)
//    private String school;
//    will be a separate entity
}
