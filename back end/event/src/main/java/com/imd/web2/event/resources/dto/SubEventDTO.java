package com.imd.web2.event.resources.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.imd.web2.event.model.EventType;

import jakarta.persistence.Id;
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
public class SubEventDTO implements Serializable{
    @Id
    private Integer id;
    @NotNull
    @Size(max = 300)
    private String description;
    @NotNull
    @Size(max = 150)
    private String name;
    @NotNull
    private EventType type;
    @NotNull
    private LocalDateTime hours;
    @NotNull
    @Size(max = 150)
    private String location;
    @NotNull
    private EventDTO eventDTO;
}
