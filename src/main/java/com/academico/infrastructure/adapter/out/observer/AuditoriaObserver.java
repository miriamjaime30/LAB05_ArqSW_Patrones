package com.academico.infrastructure.adapter.out.observer;

import com.academico.application.observer.EstudianteObserver;
import com.academico.application.observer.TipoEvento;
import com.academico.domain.model.Estudiante;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

public class AuditoriaObserver implements EstudianteObserver {
    public static final DateTimeFormatter FORMATO= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    @Override
    public void notificar(TipoEvento tipo, Estudiante estudiante){
        System.out.println("[AUDITORIA] " + LocalDateTime.now().format(FORMATO) + "-" + tipo + " -id=" + estudiante.getId() + ", nombre=" + estudiante.getNombre());
    }
}
