package imd.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Participant;

import java.util.Optional;


public interface IParticipantRepository extends JpaRepository<Participant,Integer> {

}
