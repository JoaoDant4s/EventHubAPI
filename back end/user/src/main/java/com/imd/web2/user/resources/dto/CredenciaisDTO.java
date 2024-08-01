package com.imd.web2.user.resources.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {
    @NotNull(message = "Senha é obrigatória")
    @Size(max = 255)
    private String email;
    @NotNull(message = "Senha é obrigatória")
    @Size(max = 255)
    private String password;
}
