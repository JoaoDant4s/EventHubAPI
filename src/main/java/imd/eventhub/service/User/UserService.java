package imd.eventhub.service.User;


import imd.eventhub.exception.NotFoundException;
import imd.eventhub.model.Participant;
import imd.eventhub.model.Person;
import imd.eventhub.model.User;
import imd.eventhub.repository.IParticipantRepository;
import imd.eventhub.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public User save(User user) {
        if(user.getName().isEmpty()){
            throw new NotFoundException("Nome não foi encontrado");
        }
        if(user.getCpf().isEmpty()){
            throw new NotFoundException("CPF não foi encontrado");
        }
        userRepository.save(user);

        return user;
    }

    @Override
    public void update(User user){
        userRepository.save(user);
    }

    @Override
    public void delete(User user){userRepository.delete(user);
    }

    @Override
    public Optional<User> getById(Integer id){
        return userRepository.findById(id);
    }

    @Override
    public List<User> getList(){
        return userRepository.findAll();
    }
}
