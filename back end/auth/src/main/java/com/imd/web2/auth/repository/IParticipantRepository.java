package com.imd.web2.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.auth.model.Participant;

public interface IParticipantRepository extends JpaRepository<Participant,Integer> {

}
