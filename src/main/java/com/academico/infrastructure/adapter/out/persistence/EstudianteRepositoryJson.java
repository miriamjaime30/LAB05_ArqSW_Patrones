
package com.academico.infrastructure.adapter.out.persistence;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstudianteRepositoryJson implements EstudianteRepository {

    private final Path archivo = Path.of("data", "estudiantes.json");

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final Type tipoLista =
            new TypeToken<List<Estudiante>>() {}.getType();

    private List<Estudiante> leerArchivo() {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        try (Reader reader = Files.newBufferedReader(archivo)) {
            List<Estudiante> estudiantes =
                    gson.fromJson(reader, tipoLista);

            return estudiantes != null
                    ? estudiantes
                    : new ArrayList<>();

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo leer el archivo JSON.", e);
        }
    }

    private void escribirArchivo(List<Estudiante> estudiantes) {
        try {
            Files.createDirectories(archivo.getParent());

            try (Writer writer = Files.newBufferedWriter(archivo)) {
                gson.toJson(estudiantes, writer);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo escribir el archivo JSON.", e);
        }
    }

    @Override
    public void guardar(Estudiante estudiante) {
        List<Estudiante> estudiantes = leerArchivo();

        boolean existe = estudiantes.stream()
                .anyMatch(e -> e.getId() == estudiante.getId());

        if (existe) {
            throw new IllegalArgumentException(
                    "Ya existe un estudiante con ese ID.");
        }

        estudiantes.add(estudiante);
        escribirArchivo(estudiantes);
    }

    @Override
    public List<Estudiante> listar() {
        return leerArchivo();
    }

    @Override
    public Optional<Estudiante> buscarPorId(int id) {
        return leerArchivo().stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    @Override
    public boolean actualizar(Estudiante estudiante) {
        List<Estudiante> estudiantes = leerArchivo();

        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getId() == estudiante.getId()) {
                estudiantes.set(i, estudiante);
                escribirArchivo(estudiantes);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {
        List<Estudiante> estudiantes = leerArchivo();

        boolean eliminado = estudiantes.removeIf(
                e -> e.getId() == id);

        if (eliminado) {
            escribirArchivo(estudiantes);
        }

        return eliminado;
    }
}
