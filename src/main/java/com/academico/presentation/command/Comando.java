package com.academico.presentation.command;

import java.util.Scanner;

public interface Comando {
    String getDescripcion();
    void ejecutar(Scanner sc);
}
