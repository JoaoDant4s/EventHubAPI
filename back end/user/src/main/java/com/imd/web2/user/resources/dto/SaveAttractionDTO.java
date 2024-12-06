package com.imd.web2.user.resources.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveAttractionDTO {
    @Size(max = 255)
    private String description;
    @NotNull(message = "Contato é obrigatório")
    @Size(max = 20)
    private String contact;
    public SaveAttractionDTO(){}
}
