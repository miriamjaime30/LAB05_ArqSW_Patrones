package com.academico.presentation;

import com.academico.domain.model.Curso;
import com.academico.application.service.CursoService;

import java.util.Scanner;

public class CursoUI {

    private static final CursoService service =
            new CursoService();

    public static void mostrarMenu(Scanner sc) {

        int opcion;

        do {

            System.out.println("\n--- CURSOS ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("0. Regresar");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Créditos: ");
                    int creditos = sc.nextInt();

                    service.registrar(
                            new Curso(id, nombre, creditos)
                    );

                    System.out.println("Curso registrado.");
                    break;

                case 2:
                    service.listar().forEach(c ->
                            System.out.println(
                                    c.getId() + " - " +
                                            c.getNombre() + " - " +
                                            c.getCreditos() + " créditos"
                            )
                    );
                    break;

                case 3:
                    System.out.print("ID del curso: ");
                    int idAct = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nuevo nombre: ");
                    String nombreAct = sc.nextLine();

                    System.out.print("Nuevos créditos: ");
                    int creditosAct = sc.nextInt();

                    boolean actualizado = service.actualizar(
                            new Curso(
                                    idAct,
                                    nombreAct,
                                    creditosAct
                            )
                    );

                    System.out.println(
                            actualizado
                                    ? "Curso actualizado."
                                    : "Curso no encontrado."
                    );
                    break;

                case 4:
                    System.out.print("ID a eliminar: ");
                    int idEliminar = sc.nextInt();

                    boolean eliminado =
                            service.eliminar(idEliminar);

                    System.out.println(
                            eliminado
                                    ? "Curso eliminado."
                                    : "Curso no encontrado."
                    );
                    break;
            }

        } while (opcion != 0);
    }
}
