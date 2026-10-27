package com.academico.application.validation;

import com.academico.domain.model.Estudiante;

public class ValidadorId extends ValidadorEstudiante{
    @Override
    protected void comprobar(Estudiante estudiante){
        if(estudiante.getId()<0){
            throw new IllegalArgumentException("El ID no puede ser negativo");

        }
    }
}
