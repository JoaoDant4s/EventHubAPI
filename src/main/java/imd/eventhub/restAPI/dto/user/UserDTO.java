package imd.eventhub.restAPI.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String cpf;
    private String birthDate;
    private Integer age;
    private Integer attractionId;
}
