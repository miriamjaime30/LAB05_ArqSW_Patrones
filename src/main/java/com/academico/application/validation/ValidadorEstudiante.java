package com.academico.application.validation;

import com.academico.domain.model.Estudiante;

public abstract class ValidadorEstudiante {
    private ValidadorEstudiante siguiente;
    public ValidadorEstudiante setSiguiente(ValidadorEstudiante siguiente){
        this.siguiente=siguiente;
        return siguiente;
    }
    public void validar(Estudiante estudiante){
        comprobar(estudiante);
        if(siguiente !=null){
            siguiente.validar(estudiante);
        }
    }
    protected abstract void comprobar(Estudiante estudiante);
}
