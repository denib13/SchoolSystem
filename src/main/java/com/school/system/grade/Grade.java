package com.school.system.grade;

import com.school.system.school.School;
import com.school.system.users.student.Student;
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
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, name = "classGroup")
    private Character group;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Student> students;
}
