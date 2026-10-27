package com.academico;
import com.academico.application.validation.*;
import com.academico.infrastructure.adapter.in.RepositoryFactory;
import com.academico.infrastructure.adapter.out.observer.AuditoriaObserver;
import com.academico.infrastructure.adapter.out.observer.CorreoObserver;
import com.academico.infrastructure.adapter.out.persistence.EstudianteRepositoryJson;
import com.academico.infrastructure.adapter.out.persistence.EstudianteRepositoryPostgres;
import com.academico.infrastructure.config.DatabaseConfig;
import com.academico.presentation.EstudianteUI;

import com.academico.application.service.EstudianteService;
import com.academico.domain.repository.EstudianteRepository;

import  java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Demostración del patrón Singleton
        DatabaseConfig a=DatabaseConfig.getInstance();
        DatabaseConfig b=DatabaseConfig.getInstance();
        System.out.println("¿Misma instancia? " + (a == b));
        EstudianteRepository repository= RepositoryFactory.crear();

        // 1. Adaptador de salida: almacena en JSON
        //EstudianteRepository repository=new EstudianteRepositoryJson();
        //EstudianteRepository repository=new EstudianteRepositoryPostgres();


        //2. Nucleo de la aplicacion: caso de uso
        ValidadorEstudiante validador=new ValidadorId();
        validador.setSiguiente(new ValidadorNombre()).setSiguiente(new ValidadorCorreo()).setSiguiente(new ValidadorCorreoUnico(repository));
        EstudianteService service=new EstudianteService(repository, validador);

        service.suscribir(new AuditoriaObserver());
        service.suscribir(new CorreoObserver());

        // presentacion
        EstudianteUI estudianteUI=new EstudianteUI(service);


        Scanner sc = new Scanner(System.in);

        int opcion;

        do {

            System.out.println("\n=== SISTEMA DE GESTIÓN ACADÉMICA ===");
            System.out.println("1. Gestionar estudiantes");
            System.out.println("2. Gestionar cursos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:
                    estudianteUI.mostrarMenu(sc);
                    break;

                case 2:
                   // CursoUI.mostrarMenu(sc);
                    break;

                case 0:
                    System.out.println("Sistema finalizado.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}