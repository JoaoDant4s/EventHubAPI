package imd.eventhub.service.User;


import imd.eventhub.exception.CpfNotValidException;
import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.User;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.service.Attraction.IAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAttractionService attractionService;

    @Override
    public List<User> getList(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }
        return user;
    }

    @Override
    public User save(SaveUserDTO userDTO) {
        Optional<User> userWithSameCPF = getUserByCPF(userDTO.getCpf());

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


        return savedUser;
    }

    @Override
    public Optional<User> update(UpdateUserDTO user){
        Optional<User> getUser = getById(user.getId());
        Optional<User> userWithSameCPF = getUserByCPF(user.getCpf());

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

        userRepository.save(getUser.get());
        return getUser;
    }

    @Override
    public void delete(Integer id){
        Optional<User> user = getById(id);
        if(user.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }
        userRepository.delete(user.get());
    }

    @Override
    public Optional<User> getUserByCPF(String cpf){
        return userRepository.findByCpf(cpf);
    }

    @Override
    public void setUserAttraction(Integer userId, Integer attractionId) {
        Optional<User> user = getById(userId);
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
    public Optional<User> getUserByAttractionId(Integer attractionId) {
        Optional<User> user = userRepository.findByAttraction_id(attractionId);

        if(user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return user;
    }


    public static boolean checkCpfIsValid(String cpf){

        Pattern regex = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
        return regex.matcher(cpf).find();

    }
}
