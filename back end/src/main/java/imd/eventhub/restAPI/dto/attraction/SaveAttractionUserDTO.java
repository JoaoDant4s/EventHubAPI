package imd.eventhub.restAPI.dto.attraction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SaveAttractionUserDTO {
    @NotNull(message = "Email é obrigatório")
    @Size(max = 255)
    private String email;
    @NotNull(message = "Senha é obrigatória")
    @Size(max = 255)
    private String password;
    @NotNull(message = "Confirmação de senha é obrigatória")
    @Size(max = 255)
    private String confirmPassword;
    @NotNull(message = "Nome é obrigatório")
    @Size(max = 100)
    private String name;
    @NotNull(message = "CPF/CNPJ é obrigatório")
    @Size(max = 18)
    private String cpf;
    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate birthDate;
    private SaveAttractionDTO attraction;

    public SaveAttractionUserDTO(){};
}
