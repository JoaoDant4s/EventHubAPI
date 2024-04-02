package imd.eventuhub.repository;

import imd.eventuhub.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParticipantRepository extends JpaRepository<Participant,Integer> {
}
