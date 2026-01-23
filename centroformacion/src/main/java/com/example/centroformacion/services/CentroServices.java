package com.example.centroformacion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.centroformacion.entity.Curso;
import com.example.centroformacion.entity.Especialidad;
import com.example.centroformacion.entity.Profesor;
import com.example.centroformacion.repository.CursoRepository;
import com.example.centroformacion.repository.ProfesorRepository;

@Service
public class CentroServices {

    //inyeccion de repositorios


    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    //PROFESORES

    //Todos los profesores
    public List<Profesor> getAllProfesores(){
        return profesorRepository.findAll();
    }

    //Profesor por id
    public Profesor getProfesorById(String id){
        return profesorRepository.findById(id).orElse(null);
    }

    //Agregar profesor 
    public void addProfesor(Profesor profesor) {
        if (profesor != null) {
            profesorRepository.save(profesor);
        }
    }

    //Actualizar especialidad por id
    public boolean actualizarEspecialidad(Especialidad especialidad, String id) {
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) return false;
        profesor.setEspecialidad(especialidad);
        profesorRepository.save(profesor);
        return true;
    }

    //Eliminar profesor 
    public boolean eliminarProfesor(String id) {
        if (!profesorRepository.existsById(id)) return false;
        profesorRepository.deleteById(id);
        return true;
    }


    //CURSOS

    //Listar todos los cursos
    public List<Curso> getAllCursos(){
        return cursoRepository.findAll();
    }

    //Curso por id
    public Curso getCursoById(String id){
        return cursoRepository.findById(id).orElse(null);
    }

    //Agregar curso
    public void addCurso(Curso curso) {
        if (curso != null) {
            cursoRepository.save(curso);
        }
    }

    //Eliminar curso
    public boolean eliminarCurso(String id) {
        if (!cursoRepository.existsById(id)) return false;
        cursoRepository.deleteById(id);
        return true;
    }

    //Actualizar profesor del curso asignado
    // public boolean actualizarProfesor(Profesor profesor, String id) {
    //     Curso curso = cursoRepository.findById(id).orElse(null);
    //     if (profesor == null) return false;
    //     profesor.getNombre(profesor);
    //     profesorRepository.save(profesor);
    //     return true;
    // }


}
