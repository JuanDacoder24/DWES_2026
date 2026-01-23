package com.example.centroformacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.centroformacion.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, String> {

}
