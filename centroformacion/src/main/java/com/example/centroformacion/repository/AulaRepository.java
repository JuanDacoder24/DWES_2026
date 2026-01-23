package com.example.centroformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.centroformacion.entity.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, String> {

}
