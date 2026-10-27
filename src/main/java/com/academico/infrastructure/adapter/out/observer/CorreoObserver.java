package com.academico.infrastructure.adapter.out.observer;

import com.academico.application.observer.EstudianteObserver;
import com.academico.application.observer.TipoEvento;
import com.academico.domain.model.Estudiante;

public class CorreoObserver implements EstudianteObserver {
    @Override
    public void notificar(TipoEvento tipo, Estudiante estudiante){
        String mensaje = switch (tipo){
            case REGISTRADO -> "Bienvenido(a), tu registro fue exitoso";
            case ACTUALIZADO -> "Tus datos fueron actualizados";
            case ELIMINADO -> "Tu registro fue eliminado del sistema";
        };
        System.out.println("[CORREO] Para: " + estudiante.getCorreo() + " | " + mensaje);
    }
}
