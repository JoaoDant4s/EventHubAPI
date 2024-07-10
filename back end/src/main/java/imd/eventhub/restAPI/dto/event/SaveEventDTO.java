package imd.eventhub.restAPI.dto.event;

import java.time.LocalDateTime;

import imd.eventhub.model.EventType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveEventDTO {
    @NotNull(message = "Descricão é obrigatório")
    @Size(max = 300)
    private String description;
    @NotNull(message = "Nome é obrigatório")
    @Size(max = 100)
    private String name;
    @NotNull(message = "Data inicial é obrigatória")
    private LocalDateTime initialDate;
    @NotNull(message = "Data final é obrigatória")
    private LocalDateTime finalDate;
    @NotNull(message = "Capacidade máxima é obrigatória")
    private Integer maximumCapacity;
    @NotNull(message = "Tipo é obrigatório")
    private EventType type;
    @NotNull(message = "Endereço é obrigatório")
    @Size(max = 100)
    private String address;
}
