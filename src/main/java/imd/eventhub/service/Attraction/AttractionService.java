package imd.eventhub.service.Attraction;

import imd.eventhub.exception.ContactNotValidException;
import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.repository.IAttractionRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionDTO;
import imd.eventhub.restAPI.dto.attraction.SaveAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.ShowAttractionUserDTO;
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import imd.eventhub.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    @Override
    public Optional<UserDTO> getById(Integer attractionId){
        Optional<User> user = userRepository.findByAttraction_id(attractionId);

        if(user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        Optional<UserDTO> userDTO = Optional.of(UserDTO.convertUserToUserDTO(user.get()));

        return userDTO;
    }

    @Override
    public List<UserDTO> getList(){
        return userRepository.findByAttractionIsNotNull().stream().map(user-> {
            UserDTO userDTO = UserDTO.convertUserToUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Attraction save(SaveAttractionDTO attractionDTO){

        if(attractionDTO.getDescription() == null){
            throw new NotFoundException("campo 'description' não foi encontrado");
        }
        if(attractionDTO.getContact() == null){
            throw new NotFoundException("campo 'contact' não foi encontrado");
        }
        if(!checkContactIsValid(attractionDTO.getContact())){
            throw new ContactNotValidException(String.format("O contato digitado ('%s') não segue o padrão (__) _____-____", attractionDTO.getContact()));
        }

        Attraction attraction = new Attraction();
        attraction.setDescription(attractionDTO.getDescription());
        attraction.setContact(attractionDTO.getContact());

        return attractionRepository.save(attraction);
    }
    @Override
    public UserDTO save(SaveAttractionUserDTO attractionUserDTO) {

        SaveUserDTO userDTO = new SaveUserDTO();
        userDTO.setName(attractionUserDTO.getName());
        userDTO.setCpf(attractionUserDTO.getCpf());
        userDTO.setBirthDate(attractionUserDTO.getBirthDate());
        userDTO.setEmail(attractionUserDTO.getEmail());
        userDTO.setPassword(attractionUserDTO.getPassword());
        UserDTO savedUser = userService.save(userDTO);

        SaveAttractionDTO attraction = new SaveAttractionDTO();
        attraction.setDescription(attractionUserDTO.getAttraction().getDescription());
        attraction.setContact(attractionUserDTO.getAttraction().getContact());
        Attraction savedAttraction = save(attraction);

        userService.setUserAttraction(savedUser.getId(), savedAttraction.getId());

        return savedUser;
    }

    @Override
    public ShowAttractionUserDTO update(UpdateAttractionDTO attractionUserDTO) {
        Optional<User> user = userRepository.findById(attractionUserDTO.getId());

        if(user.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }

        Optional<Attraction> attraction = attractionRepository.findById(user.get().getAttraction().getId());

        if(attraction.isEmpty()){
            throw new NotFoundException("Atração não encontrada");
        }
        UpdateUserDTO userDTO = new UpdateUserDTO();
        userDTO.setId(attractionUserDTO.getId());
        userDTO.setEmail(attractionUserDTO.getEmail());
        userDTO.setPassword(attractionUserDTO.getPassword());
        userDTO.setName(attractionUserDTO.getName());
        userDTO.setCpf(attractionUserDTO.getCpf());
        userDTO.setBirthDate(attractionUserDTO.getBirthDate());

        userService.update(userDTO);

        if(attraction.isPresent()){
            if(attraction.get().getDescription() == null){
                throw new NotFoundException("campo 'description' não foi encontrado");
            }
            if(attraction.get().getContact() == null){
                throw new NotFoundException("campo 'contact' não foi encontrado");
            }
            if(!checkContactIsValid(attractionUserDTO.getAttraction().getContact())){
                throw new ContactNotValidException(String.format("O contato digitado ('%s') não segue o padrão (__) _____-____", attractionUserDTO.getAttraction().getContact()));
            }
        }

        attraction.get().setDescription(attractionUserDTO.getAttraction().getDescription());
        attraction.get().setContact(attractionUserDTO.getAttraction().getContact());

        SaveAttractionDTO attractionDto = new SaveAttractionDTO();
        attractionDto.setDescription(attractionUserDTO.getAttraction().getDescription());
        attractionDto.setContact(attractionUserDTO.getAttraction().getContact());

        ShowAttractionUserDTO showAttractionDTO = new ShowAttractionUserDTO();
        showAttractionDTO.setId(attractionUserDTO.getId());
        showAttractionDTO.setEmail(attractionUserDTO.getEmail());
        showAttractionDTO.setPassword(attractionUserDTO.getPassword());
        showAttractionDTO.setName(attractionUserDTO.getName());
        showAttractionDTO.setCpf(attractionUserDTO.getCpf());
        showAttractionDTO.setBirthDate(attractionUserDTO.getBirthDate());
        showAttractionDTO.setAttraction(attractionDto);

        attractionRepository.save(attraction.get());

        return showAttractionDTO;
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
