package com.academico.presentation.command;

import com.academico.application.port.in.EstudianteUseCase;
import com.academico.domain.model.Estudiante;

import java.util.Scanner;

public class ActualizarEstudianteComando implements Comando{
    private final EstudianteUseCase service;
    public ActualizarEstudianteComando(EstudianteUseCase service){
        this.service=service;
    }

    @Override
    public String getDescripcion() {
        return "Actualizar";
    }
    @Override
    public void ejecutar(Scanner sc){
        System.out.print("ID del estudiante: ");
        int id=sc.nextInt();
        sc.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre= sc.nextLine();
        System.out.print("Nuevo correo: ");
        String correo=sc.nextLine();

        try{
            boolean actualizado=service.actualizar(new Estudiante(id, nombre, correo));
            System.out.println(actualizado
                    ?"Estudiante actualizado"
                    :"Estudiante no encontrado");
        }catch (IllegalArgumentException e){
            System.out.println("No se pudo actualizar: " + e.getMessage());
        }
    }
}
