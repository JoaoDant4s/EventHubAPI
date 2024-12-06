package com.imd.web2.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.user.model.Participant;

public interface IParticipantRepository extends JpaRepository<Participant,Integer> {

}
