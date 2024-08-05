package imd.eventhub.restAPI.dto.user;

import imd.eventhub.model.User;
import imd.eventhub.restAPI.dto.participant.ParticipantDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private LocalDate birthDate;
    private Integer age;
    private Integer attractionId;
    private Integer participantId;
    private Boolean promoter;

    public UserDTO(){}

    public static UserDTO toUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setCpf(user.getCpf());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setAge(user.getAge());
        userDTO.setPromoter(user.isPromoter());
        if (user.getAttraction() != null) {
            userDTO.setAttractionId(user.getAttraction().getId());
        } else {
            userDTO.setAttractionId(null);
        }
        if (user.getParticipant() != null) {
            userDTO.setParticipantId(user.getParticipant().getId());
        } else {
            userDTO.setParticipantId(null);
        }

        return userDTO;
    }
}
