package com.example.centroformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.centroformacion.entity.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String> {

}
