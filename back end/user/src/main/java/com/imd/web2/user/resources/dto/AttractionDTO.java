package com.imd.web2.user.resources.dto;

import com.imd.web2.user.model.Attraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttractionDTO {
    private Integer id;
    private String description;
    private String contact;
    private Integer userId;
    public AttractionDTO(){}

    public static AttractionDTO toAttractionDTO(Attraction attraction){
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(attraction.getId());
        attractionDTO.setDescription(attraction.getDescription());
        attractionDTO.setContact(attraction.getContact());
        attractionDTO.setUserId(attraction.getUser().getId());
        return attractionDTO;
    }
}
