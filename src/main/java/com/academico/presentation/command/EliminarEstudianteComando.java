package com.academico.presentation.command;

import com.academico.application.port.in.EstudianteUseCase;

import java.util.Scanner;

public class EliminarEstudianteComando implements Comando{
    private final EstudianteUseCase service;
    public EliminarEstudianteComando(EstudianteUseCase service){
        this.service=service;
    }

    @Override
    public String getDescripcion() {
        return "Eliminar";
    }
    @Override
    public void ejecutar(Scanner sc){
        System.out.print("ID a eliminar: ");
        int id=sc.nextInt();
        sc.nextLine();
        boolean eliminado=service.eliminar(id);
        System.out.println(eliminado
                ?"Estudiante eliminado"
                :"Estudiante no encontrado");
    }
}
