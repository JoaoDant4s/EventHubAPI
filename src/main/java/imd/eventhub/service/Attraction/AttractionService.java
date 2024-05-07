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
import imd.eventhub.restAPI.dto.attraction.UpdateAttractionDTO;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
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
    public Optional<Attraction> getById(Integer id){
        Optional<Attraction> attraction = attractionRepository.findById(id);
        if(attraction.isEmpty()){
            throw new NotFoundException("Atração não encontrada");
        }
        return attraction;
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
        UserDTO savedUser = userService.save(userDTO);

        SaveAttractionDTO attraction = new SaveAttractionDTO();
        attraction.setDescription(attractionUserDTO.getDescription());
        attraction.setContact(attractionUserDTO.getContact());

        Attraction savedAttraction = save(attraction);

        userService.setUserAttraction(savedUser.getId(), savedAttraction.getId());

        return savedUser;
    }

    @Override
    public Attraction update(UpdateAttractionDTO attractionUserDTO) {

        Optional<Attraction> attraction = getById(attractionUserDTO.getId());
        if(attraction.isEmpty()){
            throw new NotFoundException("Atração não encontrada");
        }

        if(attraction.isPresent()){
            if(attraction.get().getDescription() == null){
                throw new NotFoundException("campo 'description' não foi encontrado");
            }
            if(attraction.get().getContact() == null){
                throw new NotFoundException("campo 'contact' não foi encontrado");
            }
            if(!checkContactIsValid(attraction.get().getContact())){
                throw new ContactNotValidException(String.format("O contato digitado ('%s') não segue o padrão (__) _____-____", attraction.get().getContact()));
            }
        }
        attraction.get().setDescription(attractionUserDTO.getDescription());
        attraction.get().setContact(attractionUserDTO.getContact());

        return attractionRepository.save(attraction.get());
    }

    @Override
    public void delete(Integer id) {
        Optional<Attraction> attraction = getById(id);
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
