package com.example.apirestfederacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apirestfederacion.entity.Arbitro;
import com.example.apirestfederacion.entity.Rol;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, String> {

    List<Arbitro> findByRol(Rol rol);

}
