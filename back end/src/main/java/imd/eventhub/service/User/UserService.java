package imd.eventhub.service.User;


import imd.eventhub.exception.*;
import imd.eventhub.model.Attraction;
import imd.eventhub.model.Participant;
import imd.eventhub.model.User;
import imd.eventhub.repository.IAttractionRepository;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.repository.IUserRepository;
import imd.eventhub.restAPI.dto.participant.SaveParticipantDTO;
import imd.eventhub.restAPI.dto.user.SaveUserDTO;
import imd.eventhub.restAPI.dto.user.UpdateUserDTO;
import imd.eventhub.restAPI.dto.user.UserDTO;
import imd.eventhub.service.Attraction.IAttractionService;
import imd.eventhub.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAttractionRepository attractionRepository;
    @Autowired
    IAttractionService attractionService;
    @Autowired
    IParticipantRepository participantRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getList(){
        return userRepository.findAll().stream().map(user-> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getPromoterList() {
        return userRepository.getPromoters().stream().map(user-> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO savePromoter(SaveParticipantDTO partDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {


        boolean checkUser = isValid(
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
            user.setPromoter(true);
            User savedUser = userRepository.save(user);

            UserDTO showUser = UserDTO.toUserDTO(savedUser);
            return showUser;
        }

        return null;
    }

    @Override
    public Optional<UserDTO> getById(Integer id){
        Optional<UserDTO> showUser = userRepository.findById(id).stream().findAny().map(user-> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
        if(showUser.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }
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
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).stream().findAny().map(user -> {
            UserDTO userDTO = UserDTO.toUserDTO(user);
            return userDTO;
        });
    }

    @Override
    public void setUserAttraction(Integer userId, Integer attractionId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Attraction> attraction = attractionRepository.findById(attractionId);

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
    public boolean isValid(SaveUserDTO userDTO) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {
        Optional<UserDTO> userWithSameCPF = getUserByCPF(userDTO.getCpf());
        Optional<UserDTO> userWithSameEmail = getUserByEmail(userDTO.getEmail());

        if(userDTO.getName() == null) throw new NullParameterException("O nome não foi encontrado");
        if(userDTO.getEmail() == null)throw new NullParameterException("O email não foi encontrado");
        if(userDTO.getCpf() == null) throw new NullParameterException("O CPF não foi encontrado");
        if(userDTO.getBirthDate() == null) throw new NullParameterException("A data de nascimento não foi encontrada");
        if(userDTO.getPassword() == null) throw new NullParameterException("A senha não foi encontrada");
        if(userWithSameEmail.isPresent()) throw new EmailNotValidException("O Email digitado já está associado a um outro usuário");
        if(userDTO.getPassword().length() < 8) throw new PasswordNotValidException("A senha precisa ter no mínimo 8 caracteres");
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) throw new PasswordNotValidException("As senhas não são iguais");
        if(!checkCpfIsValid(userDTO.getCpf())) throw new CpfNotValidException(String.format("O CPF digitado ('%s') não segue o padrão 000.000.000-00", userDTO.getCpf()));
        if(userWithSameCPF.isPresent()) throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
        if(userDTO.getBirthDate().isAfter(LocalDate.now())) throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");

        return true;
    }

    @Override
    public boolean updateIsValid(SaveUserDTO userDTO, Integer userId) throws NullParameterException, EmailNotValidException, PasswordNotValidException, CpfNotValidException, DateOutOfRangeException {
        Optional<UserDTO> userWithSameCPF = getUserByCPF(userDTO.getCpf());
        Optional<UserDTO> userWithSameEmail = getUserByEmail(userDTO.getEmail());
        Optional<User> user = userRepository.findById(userId);

        if(userDTO.getName() == null) throw new NullParameterException("O nome não foi encontrado");
        if(userDTO.getEmail() == null)throw new NullParameterException("O email não foi encontrado");
        if(userDTO.getCpf() == null) throw new NullParameterException("O CPF não foi encontrado");
        if(userDTO.getBirthDate() == null) throw new NullParameterException("A data de nascimento não foi encontrada");
        if(userDTO.getPassword() == null) throw new NullParameterException("A senha não foi encontrada");
        if(userWithSameEmail.isPresent() && !user.get().getEmail().equals(userDTO.getEmail())) throw new EmailNotValidException("O Email digitado já está associado a um outro usuário");
        if(userDTO.getPassword().length() < 8) throw new PasswordNotValidException("A senha precisa ter no mínimo 8 caracteres");
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) throw new PasswordNotValidException("As senhas não são iguais");
        if(!checkCpfIsValid(userDTO.getCpf())) throw new CpfNotValidException(String.format("O CPF digitado ('%s') não segue o padrão 000.000.000-00", userDTO.getCpf()));
        if(userWithSameCPF.isPresent() && !user.get().getCpf().equals(userDTO.getCpf())) throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
        if(userDTO.getBirthDate().isAfter(LocalDate.now())) throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");

        return true;
    }


    @Override
    public boolean updateInfoIsValid(UpdateUserDTO userDTO, Integer userId) throws NullParameterException, CpfNotValidException, DateOutOfRangeException {
        Optional<UserDTO> userWithSameCPF = getUserByCPF(userDTO.getCpf());
        Optional<User> user = userRepository.findById(userId);

        if(userDTO.getName() == null) throw new NullParameterException("O nome não foi encontrado");
        if(userDTO.getCpf() == null) throw new NullParameterException("O CPF não foi encontrado");
        if(userDTO.getBirthDate() == null) throw new NullParameterException("A data de nascimento não foi encontrada");
        if(!checkCpfIsValid(userDTO.getCpf())) throw new CpfNotValidException("O CPF/CNPJ digitado não segue o padrão");
        if(userWithSameCPF.isPresent() && !user.get().getCpf().equals(userDTO.getCpf())) throw new CpfNotValidException("O CPF digitado já está associado a um outro usuário");
        if(userDTO.getBirthDate().isAfter(LocalDate.now())) throw new DateOutOfRangeException("A data informada é maior do que a data de hoje");

        return true;
    }

    public static boolean checkCpfIsValid(String cpf){

        Pattern regex = Pattern.compile("([0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2})|([0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2})");
        return regex.matcher(cpf).find();

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email ou senha incorreta")));

        if(user.isEmpty()){
            throw new NotFoundException("Email ou senha incorreta");
        }

        List<String> roles = new ArrayList<>();
        if(user.get().isAdmin()){
            roles.add("ADMIN");
            roles.add("USER");
        } else if(user.get().getAttraction() != null) {
            roles.add("ATTRACTION");
            roles.add("USER");
        } else if(user.get().isPromoter()){
            roles.add("PROMOTER");
        } else {
            roles.add("USER");
        }

        // Cria e retorna o objeto UserDetails com os detalhes do usuário
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .roles(roles.toArray(new String[roles.size()-1]))
                .build();

    }

    public UserDetails authentication(User user) throws PasswordNotValidException {
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        boolean checkPassword = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());
        if(checkPassword){
            return userDetails;
        }

        throw new PasswordNotValidException("Email ou senha incorretos");
    }
}
