package com.academico.presentation.command;

import com.academico.application.port.in.EstudianteUseCase;
import com.academico.domain.model.Estudiante;

import java.util.Scanner;

public class RegistrarEstudianteComando implements Comando{
    private final EstudianteUseCase service;
    public RegistrarEstudianteComando(EstudianteUseCase service){
        this.service=service;
    }
    @Override
    public String getDescripcion(){
        return "Registrar";
    }
    @Override
    public void ejecutar(Scanner sc){
        System.out.print("ID: ");
        int id =sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre: ");
        String nombre=sc.nextLine();
        System.out.print("Correo: ");
        String correo=sc.nextLine();

        try{
            service.registrar(new Estudiante(id, nombre, correo));
            System.out.println("Estudiante registrado");
        }catch (IllegalArgumentException e){
            System.out.println("No se pudo registrar: "+ e.getMessage());
        }
    }
}
