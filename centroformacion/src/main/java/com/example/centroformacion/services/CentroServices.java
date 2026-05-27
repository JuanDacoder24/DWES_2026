package com.example.centroformacion.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.centroformacion.entity.Aula;
import com.example.centroformacion.entity.Especialidad;
import com.example.centroformacion.entity.Profesor;
import com.example.centroformacion.repository.AulaRepository;
import com.example.centroformacion.repository.CursoRepository;
import com.example.centroformacion.repository.ProfesorRepository;

@Service
public class CentroServices {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;


    // aulas

    public List<Aula> getAllAulas() {
        return aulaRepository.findAll();
    }

    public Optional<Aula> getAulaById(String id) {
        return aulaRepository.findById(id);
    }

    // profesores

    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    public Optional<Profesor> getProfesorById(String id) {
        return profesorRepository.findById(id);
    }

    public Profesor addProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public Optional<Profesor> modificarEspecialidadProfesor(String id, String nuevaEspecialidad) {
       return profesorRepository.findById(id).map(profesor -> {
            profesor.setEspecialidad(Especialidad.valueOf(nuevaEspecialidad));
            return profesorRepository.save(profesor);
        });
    }

}
