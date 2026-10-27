package com.academico.application.validation;

import com.academico.domain.model.Estudiante;

public class ValidadorNombre extends ValidadorEstudiante{
    @Override
    protected void comprobar(Estudiante estudiante){
        String nombre= estudiante.getNombre();
        if(nombre==null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacío");

        }
    }
}
