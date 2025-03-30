package com.school.system.school;

import com.school.system.grade.Grade;
import com.school.system.users.headmaster.Headmaster;
import com.school.system.users.student.Student;
import com.school.system.users.teacher.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "headmaster_id", referencedColumnName = "id")
    private Headmaster headmaster;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Grade> classes;

    @ManyToMany(mappedBy = "schools", cascade = CascadeType.ALL)
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Student> students;
}
