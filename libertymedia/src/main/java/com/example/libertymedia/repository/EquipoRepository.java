package com.example.libertymedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libertymedia.entity.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer>{

}
