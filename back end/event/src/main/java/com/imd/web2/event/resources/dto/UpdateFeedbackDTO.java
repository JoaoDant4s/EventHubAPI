package com.imd.web2.event.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateFeedbackDTO {
    private Integer id;
    private String comment;
    private Integer rating;

    public UpdateFeedbackDTO(){}
}
