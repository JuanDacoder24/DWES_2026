package com.example.centroformacion.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.centroformacion.entity.Aula;
import com.example.centroformacion.entity.Curso;
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

    public boolean eliminarProfesor(String id) {
        if (profesorRepository.existsById(id)) {
            profesorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // cursos

    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> getCursoById(String id) {
        return cursoRepository.findById(id);
    }

    public Curso addCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> modificarProfesor(String cursoId, String profesorId) {
    // 1. Buscamos tanto el curso como el profesor en paralelo
    Optional<Curso> cursoOpt = cursoRepository.findById(cursoId);
    Optional<Profesor> profesorOpt = profesorRepository.findById(profesorId);

    // 2. Si ambos existen en la base de datos, realizamos el cambio
    if (cursoOpt.isPresent() && profesorOpt.isPresent()) {
        Curso curso = cursoOpt.get();
        Profesor profesor = profesorOpt.get();
        
        curso.setProfesores(profesor); // Asignamos el objeto Profesor real al curso
        return Optional.of(cursoRepository.save(curso)); // Guardamos y retornamos
    }
    
    // 3. Si alguno no existe, devolvemos Empty (provocará un 404 en el controlador)
    return Optional.empty();
    }

    public boolean eliminarCurso(String id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Curso> obtenerCursoConjunto(String cursoId, String profesorId, String aulaId) {
    return cursoRepository.findById(cursoId).filter(curso -> {
        // Verificamos que el profesor asignado al curso coincida con el profesorId
        boolean mismoProfesor = curso.getProfesores() != null && 
                                curso.getProfesores().getId().equals(profesorId);
        
        // Verificamos que el aula asignada al curso coincida con el aulaId
        boolean mismaAula = curso.getAulas() != null && 
                            curso.getAulas().getId().equals(aulaId);
        
        // El filtro solo dejará pasar el curso si ambas condiciones son verdaderas
        return mismoProfesor && mismaAula;
    });
}

}
