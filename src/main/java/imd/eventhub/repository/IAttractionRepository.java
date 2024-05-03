package imd.eventhub.repository;

import imd.eventhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Attraction;

import java.util.Optional;

public interface IAttractionRepository extends JpaRepository<Attraction,Integer> {

}
