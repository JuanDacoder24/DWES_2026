package com.example.libertymedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libertymedia.entity.Piloto;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, String>{

}
