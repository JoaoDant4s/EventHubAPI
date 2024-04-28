package imd.eventhub.repository;

import imd.eventhub.model.Person;
import imd.eventhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByCpf(String cpf);
}
