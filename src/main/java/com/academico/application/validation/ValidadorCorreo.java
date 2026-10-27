package com.academico.application.validation;

import com.academico.domain.model.Estudiante;

import java.util.regex.Pattern;

public class ValidadorCorreo extends ValidadorEstudiante{

    private static final Pattern PATRON= Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    @Override
    protected void comprobar(Estudiante estudiante){
        String correo =estudiante.getCorreo();
        if(correo==null || !PATRON.matcher(correo.trim()).matches()){
            throw new IllegalArgumentException("El correo no tiene un formato válido");
        }
    }
}
