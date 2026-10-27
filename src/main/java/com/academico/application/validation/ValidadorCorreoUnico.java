package com.academico.application.validation;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;

public class ValidadorCorreoUnico extends ValidadorEstudiante{
    private final EstudianteRepository repository;
    public ValidadorCorreoUnico(EstudianteRepository repository){
        this.repository=repository;
    }
    @Override
    protected void comprobar(Estudiante estudiante){
        String correo= estudiante.getCorreo().trim();
        boolean repetido=repository.listar().stream().anyMatch(otro->otro.getId()
        != estudiante.getId() && otro.getCorreo() != null && otro.getCorreo().equalsIgnoreCase(correo));

        if (repetido) {
            throw new IllegalArgumentException("Ya existe un estudiante con ese correo");
        }
    }
}
