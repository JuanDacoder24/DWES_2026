package com.example.apirestfederacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apirestfederacion.entity.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, String> {

}
