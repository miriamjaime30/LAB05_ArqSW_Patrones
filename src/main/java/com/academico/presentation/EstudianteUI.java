package com.academico.presentation;
import com.academico.application.port.in.EstudianteUseCase;
import com.academico.domain.model.Estudiante;
import com.academico.application.service.EstudianteService;

import  java.util.Scanner;


public class EstudianteUI {

    private final EstudianteUseCase service;

    public EstudianteUI(EstudianteUseCase service){
        //
        this.service=service;
    }


    // public static void mostrarMenu(Scanner sc) {
    public void mostrarMenu(Scanner sc) {
        int opcion;
        do {

            System.out.println("\n--- ESTUDIANTES ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("0. Regresar");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1->registrar(sc);
                case 2->listar();
                case 3->actualizar(sc);
                case 4->eliminar (sc);
                case 0 ->
                        System.out.println("Regresando...");
                default ->
                        System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }


    // agregar  metodos privados
    private void registrar(Scanner sc){
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Correo: ");
        String correo = sc.nextLine();

        Estudiante estudiante =
                new Estudiante(id, nombre, correo);
        service.registrar(estudiante);

        System.out.println("Estudiante registrado.");

    }
    private void listar(){
        service.listar().forEach(e ->
                System.out.println(
                        e.getId() + " - " +
                                e.getNombre() + " - " +
                                e.getCorreo()
                )
        );
    }
    private void actualizar(Scanner sc){
        System.out.print("ID del estudiante: ");
        int idAct = sc.nextInt();
        sc.nextLine();

        System.out.print("Nuevo nombre: ");
        String nombreAct = sc.nextLine();

        System.out.print("Nuevo correo: ");
        String correoAct = sc.nextLine();

        boolean actualizado = service.actualizar(
                new Estudiante(idAct, nombreAct, correoAct)
        );

        System.out.println(
                actualizado
                        ? "Estudiante actualizado."
                        : "Estudiante no encontrado."
        );
    }
    private void eliminar(Scanner sc){
        System.out.print("ID a eliminar: ");
        int idEliminar = sc.nextInt();

        boolean eliminado =
                service.eliminar(idEliminar);

        System.out.println(
                eliminado
                        ? "Estudiante eliminado."
                        : "Estudiante no encontrado."
        );
    }
}
