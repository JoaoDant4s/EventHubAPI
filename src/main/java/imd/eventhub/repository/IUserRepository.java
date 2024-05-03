package imd.eventhub.repository;

import imd.eventhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByCpf(String cpf);
    Optional<User> findByAttraction_id(Integer attractionId);
    List<User> findByAttractionIsNotNull();

}
