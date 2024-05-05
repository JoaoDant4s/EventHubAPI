package imd.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.eventhub.model.Feedback;
import org.springframework.data.jpa.repository.Query;

public interface IFeedbackRepository extends JpaRepository<Feedback,Integer> {
}
