package imd.eventhub.restAPI.dto.ticketType;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeDTO {
    @NotNull(message = "Nome do tipo do ticket é obrigatório")
    @Size(max = 100)
    private String name;
    @NotNull(message = "Número do lote é obrigatório")
    private Integer batch;
    @NotNull(message = "Preço é obrigatório")
    private BigDecimal price;
    @NotNull(message = "Descrição é obrigatório")
    @Size(max = 200)
    private String description;
    @NotNull(message = "ID do evento é obrigatório")
    private Integer eventID;
}
