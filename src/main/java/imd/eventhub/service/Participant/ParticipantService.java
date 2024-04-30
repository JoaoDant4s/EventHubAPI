package imd.eventhub.service.Participant;

import imd.eventhub.model.Participant;
import imd.eventhub.repository.IParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParticipantService implements IParticipantService{
    @Autowired
    IParticipantRepository participantRepository;


    @Override
    public Participant save(Participant object) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional<Participant> update(Participant object, Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Participant> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Participant> getList() {
        return null;
    }
}
