package imd.eventhub.service.User;

import imd.eventhub.model.Person;
import imd.eventhub.model.User;
import imd.eventhub.service.IBaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService extends IBaseService<User> {
    public Optional<User> getUserByCPF(String cpf);
}
