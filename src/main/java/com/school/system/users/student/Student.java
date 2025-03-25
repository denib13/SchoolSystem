package com.school.system.users.student;

import com.school.system.users.parents.Parent;
import com.school.system.users.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student extends User {
    @ManyToMany(mappedBy = "children", cascade = CascadeType.ALL)
    List<Parent> parents;
//    @Column(nullable = false)
//    private String school;
//    will be a separate entity

//    @Column(nullable = false)
//    private Integer year;
//
//    @Column(nullable = false, length = 1, name = "classGroup")
//    private String group;
}
