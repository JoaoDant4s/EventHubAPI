package imd.eventhub.service.Participant;

import imd.eventhub.exception.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getList() {
        return userRepository.findByParticipantIsNotNull().stream().map(user-> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if(user.get().getParticipant()!=null){
            throw new NotFoundException("participante não encontrado");
        }

        UserDTO userDTO = UserDTO.toUserDTO(user.get());

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
    public UserDTO save(SaveParticipantDTO partDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {


        boolean checkUser = userService.isValid(
                new SaveUserDTO(
                        partDTO.getName(),
                        partDTO.getCpf(),
                        partDTO.getBirthDate().toString(),
                        partDTO.getEmail(),
                        partDTO.getPassword(),
                        partDTO.getConfirmPassword()
                )
        );

        if(checkUser){
            Participant participant = new Participant();
            Participant savedParticipant = participantRepository.save(participant);

            User user = new User();
            user.setName(partDTO.getName());
            user.setCpf(partDTO.getCpf());
            user.setEmail(partDTO.getEmail());
            user.setBirthDate(partDTO.getBirthDate());
            user.setAge((int) ChronoUnit.YEARS.between(user.getBirthDate(),LocalDate.now()));
            user.setPassword(passwordEncoder.encode(partDTO.getPassword()));
            user.setParticipant(savedParticipant);
            User savedUser = userRepository.save(user);

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }

        return null;
    }

    @Override
    public UserDTO update(UpdateParticipantDTO partDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {

        Optional<User> user = userRepository.findById(partDTO.getId());
        boolean checkUser = userService.updateIsValid(
                new SaveUserDTO(
                        partDTO.getName(),
                        partDTO.getCpf(),
                        partDTO.getBirthDate().toString(),
                        partDTO.getEmail(),
                        partDTO.getPassword(),
                        partDTO.getConfirmPassword()
                ), partDTO.getId()
        );


        if(checkUser) {

            user.get().setName(partDTO.getName());
            user.get().setCpf(partDTO.getCpf());
            user.get().setEmail(partDTO.getEmail());
            user.get().setBirthDate(partDTO.getBirthDate());
            user.get().setAge((int) ChronoUnit.YEARS.between(user.get().getBirthDate(), LocalDate.now()));
            user.get().setPassword(passwordEncoder.encode(partDTO.getPassword()));
            User savedUser = userRepository.save(user.get());

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }
        return null;
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


}
