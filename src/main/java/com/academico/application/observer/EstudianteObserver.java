package com.academico.application.observer;

import com.academico.domain.model.Estudiante;

public interface EstudianteObserver {
    void notificar(TipoEvento tipo, Estudiante estudiante);
}
