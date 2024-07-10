package imd.eventhub.service.Participant;

import imd.eventhub.exception.ContactNotValidException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.Participant;
import imd.eventhub.model.User;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.ShowAttractionUserDTO;
import imd.eventhub.restAPI.dto.participant.ParticipantDTO;
import imd.eventhub.restAPI.dto.participant.SaveParticipantDTO;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantDTO;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import imd.eventhub.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ParticipantService implements IParticipantService{
    @Autowired
    IParticipantRepository participantRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserService userService;

    @Override
    public List<UserDTO> getList() {
        return userRepository.findByParticipantIsNotNull().stream().map(user-> {
            UserDTO userDTO = UserDTO.convertUserToUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if(user.get().getParticipant()!=null){
            throw new NotFoundException("participante não encontrado");
        }

        UserDTO userDTO = UserDTO.convertUserToUserDTO(user.get());

        return userDTO;
    }

    public Optional<Participant> getParticipantById(Integer id){
        Optional<Participant> participant = participantRepository.findById(id);

        if(participant.isEmpty()){
            throw new NotFoundException("participante não encontrado");
        }
        return participant;
    }

    @Override
    public UserDTO save(SaveParticipantDTO participantDTO) {

        SaveUserDTO userDTO = new SaveUserDTO();
        userDTO.setName(participantDTO.getName());
        userDTO.setCpf(participantDTO.getCpf());
        userDTO.setBirthDate(participantDTO.getBirthDate());
        userDTO.setEmail(participantDTO.getEmail());
        userDTO.setPassword(participantDTO.getPassword());
        UserDTO savedUser = userService.save(userDTO);

        Participant participant = new Participant();
        Participant savedParticipant = participantRepository.save(participant);
        savedUser.setParticipantId(savedParticipant.getId());
        userService.setUserParticipant(savedUser.getId(), savedParticipant.getId());
        return savedUser;
    }

    @Override
    public void delete(Integer id) {
        Optional<Participant> participant = participantRepository.findById(id);
        if(participant.isEmpty()){
            throw new NotFoundException("Participante não encontrado");
        }

        Optional<User> user = userRepository.findByParticipant_id(participant.get().getId());
        if(user.isPresent()){
            user.get().setParticipant(null);
            userRepository.save(user.get());
        }

        participantRepository.delete(participant.get());
    }

    @Override
    public UserDTO update(UpdateParticipantDTO participantDTO) {

        Optional<User> user = userRepository.findById(participantDTO.getId());

        if(user.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }

        UpdateUserDTO userDTO = new UpdateUserDTO();
        userDTO.setId(participantDTO.getId());
        userDTO.setEmail(participantDTO.getEmail());
        userDTO.setPassword(participantDTO.getPassword());
        userDTO.setName(participantDTO.getName());
        userDTO.setCpf(participantDTO.getCpf());
        userDTO.setBirthDate(participantDTO.getBirthDate());

        return userService.update(userDTO);
    }


}
