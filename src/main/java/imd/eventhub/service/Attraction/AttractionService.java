package imd.eventhub.service.Attraction;

import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.repository.IAttractionRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.*;
import imd.eventhub.service.User.IUserService;
import imd.eventhub.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
        Optional<Attraction> user = attractionRepository.findById(id);
        if(user.isEmpty()){
            throw new NotFoundException("Atração não encontrada");
        }
        return user;
    }

    @Override
    public List<Attraction> getList(){
        return attractionRepository.findAll();
    }
    @Override
    public Attraction save(SaveAttractionDTO attractionDTO){

        if(attractionDTO.getDescription() == null){
            throw new NotFoundException("campo 'description' não foi encontrado");
        }
        if(attractionDTO.getContact() == null){
            throw new NotFoundException("campo 'contact' não foi encontrado");
        }

        Attraction attraction = new Attraction();
        attraction.setDescription(attractionDTO.getDescription());
        attraction.setContact(attractionDTO.getContact());

        return attractionRepository.save(attraction);
    }
    @Override
    public User save(SaveAttractionUserDTO attractionUserDTO) throws NotFoundException, CpfNotValidException, DateOutOfRangeException {

        SaveUserDTO userDTO = new SaveUserDTO();
        userDTO.setName(attractionUserDTO.getName());
        userDTO.setCpf(attractionUserDTO.getCpf());
        userDTO.setBirthDate(LocalDate.parse(attractionUserDTO.getBirthDate()));
        User savedUser;

        savedUser = userService.save(userDTO);

        SaveAttractionDTO attraction = new SaveAttractionDTO();
        attraction.setDescription(attractionUserDTO.getDescription());
        attraction.setContact(attractionUserDTO.getContact());

        Attraction savedAttraction = save(attraction);

        userService.setUserAttraction(savedUser.getId(), savedAttraction.getId());

        return savedUser;
    }

    @Override
    public Optional<Attraction> update(Attraction object, Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        Optional<Attraction> attraction = getById(id);
        if(attraction.isEmpty()){
            throw new NotFoundException("Atração não encontrado");
        }

        Optional<User> user = userService.getUserByAttractionId(attraction.get().getId());
        user.get().setAttraction(null);
        userRepository.save(user.get());

        attractionRepository.delete(attraction.get());
    }
}
