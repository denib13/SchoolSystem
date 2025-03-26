package com.school.system.users.student;

import com.school.system.users.parents.ParentRequestDTO;
import com.school.system.users.user.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO extends UserRequestDTO {
    private List<UUID> parents;
    private UUID school;
    private UUID schoolClass;
}
