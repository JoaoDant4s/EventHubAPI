package com.imd.web2.pass.resources;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.imd.web2.pass.feignclients.ParticipantFeignClient;
import com.imd.web2.pass.model.Participant;
import com.imd.web2.pass.model.Ticket;
import com.imd.web2.pass.model.TicketType;
import com.imd.web2.pass.resources.dto.SaveTicketDTO;
import com.imd.web2.pass.resources.dto.TicketDTO;
import com.imd.web2.pass.resources.exceptions.DataAlreadyExistsException;
import com.imd.web2.pass.resources.exceptions.InvalidParameterException;
import com.imd.web2.pass.resources.exceptions.NotFoundException;
import com.imd.web2.pass.resources.exceptions.NullParameterException;
import com.imd.web2.pass.resources.exceptions.BadRequestException;
import com.imd.web2.pass.services.ITicketService;
import com.imd.web2.pass.services.ITicketTypeService;

@RestController
@RequestMapping("api/ticket")
public class TicketController {

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private ITicketTypeService ticketTypeService;

    @Autowired
    private ParticipantFeignClient participantClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDTO saveTicket(@Valid @RequestBody SaveTicketDTO saveTicketDTO) {
        try {
            Ticket ticket;
            ticket = ticketService.save(fromSaveDTO(saveTicketDTO), saveTicketDTO.getDays());
            return toDTO(ticket);
        } catch (NullParameterException | DataAlreadyExistsException | InvalidParameterException e) {
            throw new BadRequestException(e.getMessage());
        } catch (NotFoundException e){
            throw e;
        }
    }

    @GetMapping("/{ticketId}")
    public TicketDTO getTicket(@PathVariable Integer ticketId) {
        Ticket ticket = ticketService.getById(ticketId).orElseThrow(() -> new NotFoundException("Nenhum ticket encontrado"));
        return toDTO(ticket);
    }

    private Ticket fromSaveDTO(SaveTicketDTO dto){
        Ticket ticket = new Ticket();

        TicketType ticketType = ticketTypeService.findTicketTypeByNameAndBatchAndEventID(dto.getTicketTypeId().getName(), dto.getTicketTypeId().getBatch(), dto.getTicketTypeId().getEventID()).orElseThrow(() -> new NotFoundException("Não existe um TicketType com esse ID"));
        ticket.setTicketType(ticketType);
        Optional<Participant> participant = Optional.ofNullable(participantClient.getParticipantById(dto.getParticipantId()).getBody());

        if(participant.isEmpty()) throw new NotFoundException("Não existe um TicketType com esse ID");

        ticket.setParticipant(participant.get());
        return ticket;

    }

    private TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setPayment(ticket.getPayment());
        dto.setPaymentStatus(dto.getPayment() == null ? "PENDENTE" : "PAGO");
        dto.setTicketTypeDTO(TicketTypeController.toDto(ticket.getTicketType()));
        return dto;
    }
}
