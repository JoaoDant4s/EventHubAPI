package imd.eventhub.service.User;


import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.Participant;
import imd.eventhub.model.User;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.feedback.FeedbackDTO;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import imd.eventhub.service.Attraction.IAttractionService;
import imd.eventhub.service.Participant.IParticipantService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAttractionService attractionService;
    @Autowired
    IParticipantRepository participantRepository;

    @Override
    public List<UserDTO> getList(){
        return userRepository.findAll().stream().map(user-> {
            UserDTO userDTO = UserDTO.convertUserToUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getById(Integer id){
        Optional<UserDTO> showUser = userRepository.findById(id).stream().findAny().map(user-> {
            UserDTO userDTO = UserDTO.convertUserToUserDTO(user);
            return userDTO;
        });
        if(showUser.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }
        return showUser;
    }

    @Override
    public UserDTO save(SaveUserDTO userDTO) {
        Optional<UserDTO> userWithSameCPF = getUserByCPF(userDTO.getCpf());

        if(userDTO.getName() == null){
            throw new NotFoundException("campo 'name' não foi encontrado");
        }
        if(userDTO.getCpf() == null){
            throw new NotFoundException("campo 'cpf' não foi encontrado");
        }
        if(userDTO.getBirthDate() == null){
            throw new NotFoundException("campo 'birthDate' não foi encontrado");
        }
        if(!checkCpfIsValid(userDTO.getCpf())){
            throw new CpfNotValidException(String.format("O CPF digitado ('%s') não segue o padrão 000.000.000-00", userDTO.getCpf()));
        }
        if(userWithSameCPF.isPresent()){
            throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
        }
        if(userDTO.getBirthDate().isAfter(LocalDate.now())){
            throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setCpf(userDTO.getCpf());
        user.setBirthDate(userDTO.getBirthDate());
        user.setAge((int) ChronoUnit.YEARS.between(user.getBirthDate(),LocalDate.now()));
        User savedUser = userRepository.save(user);

        UserDTO showUser = UserDTO.convertUserToUserDTO(savedUser);


        return showUser;
    }

    @Override
    public UserDTO update(UpdateUserDTO user){
        Optional<User> getUser = userRepository.findById(user.getId());
        Optional<UserDTO> userWithSameCPF = getUserByCPF(user.getCpf());

        if(getUser.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        } else {
            if(user.getCpf()!=null){
                if(!checkCpfIsValid(user.getCpf())){
                    throw new CpfNotValidException(String.format("O CPF digitado ('%s') não segue o padrão 000.000.000-00", user.getCpf()));
                }
                if(userWithSameCPF.isPresent()){
                    if(!userWithSameCPF.get().getCpf().equals(getUser.get().getCpf())){
                        throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
                    }
                }

                getUser.get().setCpf(user.getCpf());
            }

            if(user.getBirthDate() != null && user.getBirthDate().isAfter(LocalDate.now())){
                throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");
            } else {
                if(user.getBirthDate()!=null){
                    getUser.get().getBirthDate();
                    getUser.get().setAge((int) ChronoUnit.YEARS.between(user.getBirthDate(),LocalDate.now()));
                }
            }
            getUser.get().setName(user.getName()!=null || !user.getName().isEmpty() ? user.getName() : getUser.get().getName());
        }

        User savedUser = userRepository.save(getUser.get());

        UserDTO showUser = UserDTO.convertUserToUserDTO(savedUser);


        return showUser;
    }

    @Override
    public void delete(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }
        userRepository.delete(user.get());
    }

    @Override
    public Optional<UserDTO> getUserByCPF(String cpf) {
        return userRepository.findByCpf(cpf).stream().findAny().map(user -> {
            UserDTO userDTO = UserDTO.convertUserToUserDTO(user);
            return userDTO;
        });
    }

    @Override
    public void setUserAttraction(Integer userId, Integer attractionId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Attraction> attraction = attractionService.getById(attractionId);

        if(user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        if(attraction.isEmpty()) {
            throw new NotFoundException("Atração não encontrada");
        }

        user.get().setAttraction(attraction.get());
        userRepository.save(user.get());
    }

    @Override
    public void setUserParticipant(Integer userId, Integer participantId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Participant> participant = participantRepository.findById(participantId);

        if(user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        if(participant.isEmpty()) {
            throw new NotFoundException("Atração não encontrada");
        }

        user.get().setParticipant(participant.get());
        userRepository.save(user.get());
    }

    @Override
    public Optional<UserDTO> getUserByAttractionId(Integer attractionId) {
        Optional<User> user = userRepository.findByAttraction_id(attractionId);

        if(user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        Optional<UserDTO> userDTO = Optional.of(UserDTO.convertUserToUserDTO(user.get()));

        return userDTO;
    }


    public static boolean checkCpfIsValid(String cpf){

        Pattern regex = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
        return regex.matcher(cpf).find();

    }
}
