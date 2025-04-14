package com.school.system.users.headmaster;

import com.school.system.users.user.UserRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadmasterRequestDTO extends UserRequestDTO {
    private UUID school;
}
