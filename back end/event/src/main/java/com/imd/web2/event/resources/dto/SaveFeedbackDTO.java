package com.imd.web2.event.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveFeedbackDTO {
    private String comment;
    private Integer rating;
    private Integer userId;
    private Integer eventId;
}
