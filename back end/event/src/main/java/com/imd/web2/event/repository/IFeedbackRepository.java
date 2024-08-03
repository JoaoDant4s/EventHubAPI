package com.imd.web2.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.event.model.Feedback;

public interface IFeedbackRepository extends JpaRepository<Feedback,Integer> {
}
