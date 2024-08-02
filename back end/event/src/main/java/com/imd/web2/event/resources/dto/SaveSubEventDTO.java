package com.imd.web2.event.resources.dto;

import java.time.LocalDateTime;

import com.imd.web2.event.model.EventType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveSubEventDTO {
    @NotNull(message = "Descrição é obrigatório")
    @Size(max = 300)
    private String description;
    @NotNull(message = "Nome é obrigatório")
    @Size(max = 150)
    private String name;
    @NotNull(message = "Tipo é obrigatório")
    private EventType type;
    @NotNull(message = "Hora é obrigatório")
    private LocalDateTime hours;
    @NotNull(message = "Local é obrigatório")
    @Size(max = 150)
    private String location;
    @NotNull(message = "EventDTO é obrigatório")
    private EventDTO eventDTO;
}
