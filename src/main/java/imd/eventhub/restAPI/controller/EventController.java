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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import imd.eventhub.exception.DateOutOfRangeException;
import imd.eventhub.exception.InvalidParameterException;
import imd.eventhub.exception.NullParameterException;
import imd.eventhub.model.Event;
import imd.eventhub.restAPI.dto.event.EventDTO;
import imd.eventhub.service.Event.IEventService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/event")
public class EventController {

    @Autowired
    IEventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO saveEvent(@Valid @RequestBody EventDTO eventDTO) {
        Event event = fromDto(eventDTO);
        try {
            event = eventService.save(event);
            return toDto(event);
        } catch (NullParameterException | InvalidParameterException | DateOutOfRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/list")
    public List<EventDTO> listEvents() {
        return eventService.getList()
                .stream()
                .map(EventController::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    public EventDTO updateEvent(@Valid @RequestBody EventDTO eventDTO) {
        try {
            Event event = eventService.getByID(eventDTO.getId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhum evento com esse id"));
            try {
                event = eventService.save(mergeDto(event, eventDTO));
                return toDto(event);
            } catch (NullParameterException | InvalidParameterException | DateOutOfRangeException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable Integer id) {
        try {
            Optional<Event> event = eventService.getByID(id);
            event.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhum evento com esse id"));

            return toDto(event.get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Integer id) {
        try {
            Optional<Event> event = eventService.getByID(id);
            event.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe nenhum evento com esse id"));
            eventService.deactivate(event.get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private Event mergeDto(Event event, EventDTO dto) {
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setAddress(dto.getAddress());
        event.setId(dto.getId());
        event.setMaximumCapacity(dto.getMaximumCapacity());
        event.setInitialDate(dto.getInitialDate());
        event.setFinalDate(dto.getFinalDate());
        event.setType(dto.getType());
        return event;
    }

    private Event fromDto(EventDTO dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setAddress(dto.getAddress());
        event.setId(dto.getId());
        event.setMaximumCapacity(dto.getMaximumCapacity());
        event.setInitialDate(dto.getInitialDate());
        event.setFinalDate(dto.getFinalDate());
        event.setType(dto.getType());
        return event;
    }

    public static EventDTO toDto(Event event) {
        EventDTO dto = new EventDTO();
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setAddress(event.getAddress());
        dto.setId(event.getId());
        dto.setMaximumCapacity(event.getMaximumCapacity());
        dto.setInitialDate(event.getInitialDate());
        dto.setFinalDate(event.getFinalDate());
        dto.setType(event.getType());
        return dto;
    }
}
