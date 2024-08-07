package imd.eventhub.service.Attraction;

import imd.eventhub.exception.*;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.repository.IAttractionRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.attraction.*;
import imd.eventhub.restAPI.dto.participant.UpdateParticipantInfoDTO;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class AttractionService implements IAttractionService {
    @Autowired
    IAttractionRepository attractionRepository;

    @Autowired
    IUserService userService;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public AttractionDTO getById(Integer attractionId) throws NotFoundException {
        Optional<Attraction> attraction = attractionRepository.findById(attractionId);

        if(attraction.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO = AttractionDTO.toAttractionDTO(attraction.get());


        return attractionDTO;
    }

    @Override
    public List<UserDTO> getList(){
        return userRepository.findByAttractionIsNotNull().stream().map(user-> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateInfo(UpdateAttractionInfoDTO attrDTO) throws NullParameterException, CpfNotValidException, ContactNotValidException, DateOutOfRangeException {

        Optional<Attraction> attraction = attractionRepository.findById(attrDTO.getId());

        boolean checkUser = userService.updateInfoIsValid(
                new UpdateUserDTO(
                        attrDTO.getName(),
                        attrDTO.getCpf(),
                        attrDTO.getBirthDate().toString()
                ), attraction.get().getUser().getId()
        );

        boolean checkAttraction = isValid(new SaveAttractionDTO(attrDTO.getDescription(), attrDTO.getContact()));


        if(checkUser && checkAttraction) {

            attraction.get().setDescription(attrDTO.getDescription());
            attraction.get().setContact(attrDTO.getContact());
            attractionRepository.save(attraction.get());

            User user = attraction.get().getUser();

            user.setName(attrDTO.getName());
            user.setCpf(attrDTO.getCpf());
            user.setBirthDate(attrDTO.getBirthDate());
            user.setAge((int) ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now()));
            User savedUser = userRepository.save(user);

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }
        return null;
    }

    @Override
    public boolean isValid(SaveAttractionDTO attractionDTO) throws NullParameterException, ContactNotValidException {

        if(attractionDTO.getDescription() == null) throw new NullParameterException("campo 'description' não foi encontrado");
        if(attractionDTO.getContact() == null) throw new NullParameterException("campo 'contact' não foi encontrado");
        if(!checkContactIsValid(attractionDTO.getContact())) throw new ContactNotValidException(String.format("O contato digitado ('%s') não segue o padrão (__) _____-____", attractionDTO.getContact()));

        return true;
    }
    @Override
    public UserDTO save(SaveAttractionUserDTO attrDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, ContactNotValidException, DateOutOfRangeException {

        boolean checkUser = userService.isValid(
            new SaveUserDTO(
                attrDTO.getName(),
                attrDTO.getCpf(),
                attrDTO.getBirthDate().toString(),
                attrDTO.getEmail(),
                attrDTO.getPassword(),
                attrDTO.getConfirmPassword()
            )
        );
        boolean checkAttraction = isValid(new SaveAttractionDTO(attrDTO.getAttraction().getDescription(), attrDTO.getAttraction().getContact()));


        if(checkUser && checkAttraction) {
            Attraction attraction = new Attraction();
            attraction.setDescription(attrDTO.getAttraction().getDescription());
            attraction.setContact(attrDTO.getAttraction().getContact());
            Attraction savedAttraction = attractionRepository.save(attraction);

            User user = new User();
            user.setName(attrDTO.getName());
            user.setCpf(attrDTO.getCpf());
            user.setEmail(attrDTO.getEmail());
            user.setBirthDate(attrDTO.getBirthDate());
            user.setAge((int) ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now()));
            user.setPassword(passwordEncoder.encode(attrDTO.getPassword()));
            user.setAttraction(savedAttraction);
            User savedUser = userRepository.save(user);

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }

        return null;
    }

    @Override
    public UserDTO update(UpdateAttractionDTO attrDTO) throws  NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, ContactNotValidException, DateOutOfRangeException {
        Optional<User> user = userRepository.findById(attrDTO.getId());
        boolean checkUser = userService.updateIsValid(
                new SaveUserDTO(
                        attrDTO.getName(),
                        attrDTO.getCpf(),
                        attrDTO.getBirthDate().toString(),
                        attrDTO.getEmail(),
                        attrDTO.getPassword(),
                        attrDTO.getConfirmPassword()
                ), attrDTO.getId()
        );
        boolean checkAttraction = isValid(new SaveAttractionDTO(attrDTO.getAttraction().getDescription(), attrDTO.getAttraction().getContact()));

        if(checkUser && checkAttraction) {
            Attraction attraction = new Attraction();
            attraction.setDescription(attrDTO.getAttraction().getDescription());
            attraction.setContact(attrDTO.getAttraction().getContact());
            Attraction savedAttraction = attractionRepository.save(attraction);

            user.get().setName(attrDTO.getName());
            user.get().setCpf(attrDTO.getCpf());
            user.get().setEmail(attrDTO.getEmail());
            user.get().setBirthDate(attrDTO.getBirthDate());
            user.get().setAge((int) ChronoUnit.YEARS.between(user.get().getBirthDate(), LocalDate.now()));
            user.get().setPassword(passwordEncoder.encode(attrDTO.getPassword()));
            user.get().setAttraction(savedAttraction);
            User savedUser = userRepository.save(user.get());

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }

        return null;
    }

    @Override
    public void delete(Integer id) {
        Optional<Attraction> attraction = attractionRepository.findById(id);
        if(attraction.isEmpty()){
            throw new NotFoundException("Atração não encontrada");
        }

        Optional<User> user = userRepository.findByAttraction_id(attraction.get().getId());
        if(user.isPresent()){
            user.get().setAttraction(null);
            userRepository.save(user.get());
        }

        attractionRepository.delete(attraction.get());
    }

    public static boolean checkContactIsValid(String contact){
        Pattern regex = Pattern.compile("[(][0-9]{2}[)][ ][0-9]{5}[-][0-9]{4}");
        return regex.matcher(contact).find();
    }
}
