package com.example.libertymedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libertymedia.entity.CampeonatoPiloto;

@Repository
public interface CampeonatoPilotoRepository extends JpaRepository<CampeonatoPiloto, String>{

}
