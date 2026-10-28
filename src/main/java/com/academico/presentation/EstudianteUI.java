package com.academico.presentation;
import com.academico.application.port.in.EstudianteUseCase;
import com.academico.domain.model.Estudiante;
import com.academico.application.service.EstudianteService;
import com.academico.presentation.command.*;

import java.util.LinkedHashMap;
import java.util.Map;
import  java.util.Scanner;


public class EstudianteUI {

    private final Map<Integer, Comando> comandos= new LinkedHashMap<>();

    public EstudianteUI(EstudianteUseCase service){
        comandos.put(1, new RegistrarEstudianteComando(service));
        comandos.put(2, new ListarEstudianteComando(service));
        comandos.put(3, new ActualizarEstudianteComando(service));
        comandos.put(4, new EliminarEstudianteComando(service));
    }


    // public static void mostrarMenu(Scanner sc) {
    public void mostrarMenu(Scanner sc) {
        int opcion;
        do {

            System.out.println("\n--- ESTUDIANTES ---");
            comandos.forEach((numero, comando)->
                    System.out.println(numero + ". " + comando.getDescripcion()));
            System.out.println("0. Regresar");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            if(opcion==0){
                System.out.println("Regresando...");
            }else{
                Comando comando=comandos.get(opcion);
                if(comando != null){
                    comando.ejecutar(sc);
                }else{
                    System.out.println("Opción no válida");
                }
            }
        } while (opcion != 0);
    }
}
