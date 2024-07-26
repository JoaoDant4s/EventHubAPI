package imd.eventhub.restAPI.dto.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UpdateParticipantInfoDTO {

    private Integer id;
    private String name;
    private String cpf;
    private LocalDate birthDate;

    public UpdateParticipantInfoDTO(){}
}
