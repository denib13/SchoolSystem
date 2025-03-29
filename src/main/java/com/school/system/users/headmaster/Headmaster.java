package com.school.system.users.headmaster;

import com.school.system.school.School;
import com.school.system.users.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "headmasters")
public class Headmaster extends User {
    @OneToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;
}
