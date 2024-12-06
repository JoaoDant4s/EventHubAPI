package com.imd.web2.pass.resources.dto;

import java.time.LocalDate;
import java.util.List;

import com.imd.web2.pass.model.TicketTypeId;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveTicketDTO {

    @NotNull(message = "O ID do participante é obrigatório")
    private Integer participantId;

    @NotNull(message = "O ID do tipo de ticket é obrigatório")
    private TicketTypeId ticketTypeId;

    @NotNull(message = "A quantidade de dias é obrigatória")
    private List<LocalDate> days;

}
