package com.imd.web2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.web2.user.model.Attraction;

public interface IAttractionRepository extends JpaRepository<Attraction,Integer> {

}
