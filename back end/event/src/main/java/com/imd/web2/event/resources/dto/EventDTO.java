package com.imd.web2.event.resources.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.imd.web2.event.model.EventType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO implements Serializable{
    
    @Id
    private Integer id;
    @NotNull
    @Size(max = 300)
    private String description;
    @NotNull
    @Size(max = 100)
    private String name;
    @NotNull
    private LocalDateTime initialDate;
    @NotNull
    private LocalDateTime finalDate;
    @NotNull
    private Integer maximumCapacity;
    @NotNull
    private EventType type;
    @NotNull
    @Size(max = 100)
    private String address;
}
