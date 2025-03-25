package com.school.system.users.parents;

import com.school.system.users.student.StudentRequestDTO;
import com.school.system.users.user.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentRequestDTO extends UserRequestDTO {
    private List<UUID> children;
}
