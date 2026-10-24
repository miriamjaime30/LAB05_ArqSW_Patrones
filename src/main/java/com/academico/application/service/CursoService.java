package com.academico.application.service;

import com.academico.domain.model.Curso;
import com.academico.infrastructure.adapter.out.persistence.CursoRepository;

import java.util.List;

public class CursoService {

    private final CursoRepository repository;

    public CursoService() {
        repository = new CursoRepository();
    }

    public void registrar(Curso curso) {

        List<Curso> cursos = repository.listar();

        cursos.add(curso);

        repository.guardar(cursos);
    }

    public List<Curso> listar() {
        return repository.listar();
    }

    public boolean actualizar(Curso curso) {

        List<Curso> cursos = repository.listar();

        for (Curso c : cursos) {

            if (c.getId() == curso.getId()) {

                c.setNombre(curso.getNombre());
                c.setCreditos(curso.getCreditos());

                repository.guardar(cursos);

                return true;
            }
        }

        return false;
    }

    public boolean eliminar(int id) {

        List<Curso> cursos = repository.listar();

        boolean eliminado = cursos.removeIf(c -> c.getId() == id);

        if (eliminado) {
            repository.guardar(cursos);
        }

        return eliminado;
    }
}