package imd.eventhub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.User;
import org.springframework.data.jpa.repository.Query;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByCpf(String cpf);

    Optional<User> findByAttraction_id(Integer attractionId);

    Optional<User> findByParticipant_id(Integer participantId);

    List<User> findByAttractionIsNotNull();

    @Query(value = " select u from User u where u.attraction is null and u.admin = false and u.promoter = false ")
    List<User> getParticipants();

    @Query(value = " select u from User u where u.promoter = true ")
    List<User> getPromoters();

    Optional<User> findByEmail(String email);

}
