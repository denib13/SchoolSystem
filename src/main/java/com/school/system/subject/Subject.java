package com.school.system.subject;

import com.school.system.absence.Absence;
import com.school.system.grade.Grade;
import com.school.system.mark.Mark;
import com.school.system.remark.Remark;
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
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private Grade schoolClass;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Mark> marks;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Absence> absences;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Remark> remarks;
}
