package imd.eventuhub.repository;

import imd.eventuhub.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeedbackRepository extends JpaRepository<Feedback,Integer> {
}
