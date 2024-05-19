package imd.eventhub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByCpf(String cpf);

    Optional<User> findByAttraction_id(Integer attractionId);

    Optional<User> findByParticipant_id(Integer participantId);

    List<User> findByAttractionIsNotNull();

    List<User> findByParticipantIsNotNull();

    Optional<User> findByEmail(String email);

}
