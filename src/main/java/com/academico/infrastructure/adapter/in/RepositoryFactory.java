package com.academico.infrastructure.adapter.in;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;
import com.academico.infrastructure.adapter.out.persistence.EstudianteRepositoryJson;
import com.academico.infrastructure.adapter.out.persistence.EstudianteRepositoryPostgres;

import java.util.Locale;

public class RepositoryFactory {
    private RepositoryFactory(){
        //evitar instanciade fabrica
    }
    //metodo crear
    public static EstudianteRepository crear() {
        String tipo = System.getenv().getOrDefault("STORAGE_TYPE", "JSON");

        return switch (tipo.trim().toUpperCase()) {
            case "JSON" -> new EstudianteRepositoryJson();
            case "POSTGRES" -> new EstudianteRepositoryPostgres();
            default -> throw new IllegalArgumentException(
                    "Almacenamiento no válido: " + tipo
                            + ". Usa JSON o POSTGRES.");
        };
    }
}

