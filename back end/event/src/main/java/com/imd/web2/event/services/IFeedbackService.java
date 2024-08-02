package com.imd.web2.event.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imd.web2.event.resources.dto.FeedbackDTO;
import com.imd.web2.event.resources.dto.SaveFeedbackDTO;
import com.imd.web2.event.resources.dto.UpdateFeedbackDTO;
import com.imd.web2.event.resources.exceptions.RatingOutOfRangeException;

@Service
public interface IFeedbackService {

    public List<FeedbackDTO> getList();
    public Optional<FeedbackDTO> getById(Integer id);
    public FeedbackDTO save(SaveFeedbackDTO feedback) throws RatingOutOfRangeException;
    public FeedbackDTO update(UpdateFeedbackDTO feedback) throws RatingOutOfRangeException;
    public void delete(Integer id);
}
