package imd.eventhub.restAPI.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NotFoundException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Event;
import imd.eventhub.model.SubEvent;
import imd.eventhub.restAPI.dto.subEvent.SubEventDTO;
import imd.eventhub.service.Event.IEventService;
import imd.eventhub.service.SubEvent.ISubEventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/subEvent")
public class SubEventController {

    @Autowired
    ISubEventService subEventService;

    @Autowired
    IEventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubEventDTO saveSubEvent(@Valid @RequestBody SubEventDTO subEventDTO) {
        try {
            SubEvent subEvent = subEventService.save(fromDto(subEventDTO));
            return toDto(subEvent);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NullParameterException | DateOutOfRangeException | InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/list/{eventID}")
    public List<SubEventDTO> listSubEvents(@PathVariable Integer eventID) {
        try {
            return subEventService.getListByEventid(eventID)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NullParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/listAll")
    public List<SubEventDTO> listAllSubEvents() {
        try{
            return subEventService.getList()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        } catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
    @PutMapping
    public SubEventDTO updateSubEvent(@Valid @RequestBody SubEventDTO subEventDTO) {
        try {
            SubEvent subEvent = subEventService.getByID(subEventDTO.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhum evento com esse id"));
            subEvent = subEventService.save(mergeDto(subEvent, subEventDTO));
            return toDto(subEvent);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DateOutOfRangeException | InvalidParameterException | NullParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubEvent(@PathVariable Integer id){
        try {
            Optional<SubEvent> subEvent = subEventService.getByID(id);
            subEvent.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhum sub-evento com esse id"));
            subEventService.deactivate(subEvent.get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private SubEvent mergeDto(SubEvent subEvent, SubEventDTO dto){
        subEvent.setName(dto.getName());
        subEvent.setDescription(dto.getDescription());
        subEvent.setLocation(dto.getLocation());
        subEvent.setId(dto.getId());
        subEvent.setHours(dto.getHours());
        subEvent.setType(dto.getType());
        return subEvent;
    }

    private SubEvent fromDto(SubEventDTO dto) throws NotFoundException, NullParameterException {
        SubEvent subEvent = new SubEvent();
        subEvent.setName(dto.getName());
        subEvent.setDescription(dto.getDescription());
        subEvent.setLocation(dto.getLocation());
        subEvent.setId(dto.getId());
        subEvent.setHours(dto.getHours());
        subEvent.setType(dto.getType());
        Optional<Event> event = eventService.getByID(dto.getEventID());
        if (!event.isPresent())
            throw new NotFoundException("Não existe nenhum evento com esse id");
        subEvent.setEvent(event.get());
        return subEvent;
    }

    private SubEventDTO toDto(SubEvent subEvent) {
        SubEventDTO dto = new SubEventDTO();
        dto.setName(subEvent.getName());
        dto.setDescription(subEvent.getDescription());
        dto.setLocation(subEvent.getLocation());
        dto.setId(subEvent.getId());
        dto.setHours(subEvent.getHours());
        dto.setType(subEvent.getType());
        dto.setEventID(subEvent.getEvent().getId());
        return dto;
    }
}
