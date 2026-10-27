package com.academico.presentation.command;

import com.academico.application.port.in.EstudianteUseCase;

import java.util.Scanner;

public class ListarEstudianteComando implements Comando{
    private final EstudianteUseCase service;

    public ListarEstudianteComando(EstudianteUseCase service){
        this.service=service;
    }
    @Override
    public String getDescripcion(){
        return "Listar";
    }
    @Override
    public void ejecutar(Scanner sc){
        service.listar().forEach(e->System.out.print(e.getId() + " - "
                + e.getNombre() + " - " + e.getCorreo()));
    }
}
