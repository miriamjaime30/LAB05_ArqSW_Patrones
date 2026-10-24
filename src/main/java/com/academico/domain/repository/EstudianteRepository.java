package com.academico.domain.repository;

import com.academico.domain.model.Estudiante;

import java.util.List;
import java.util.*;

public interface EstudianteRepository {

    void guardar(Estudiante estudiante);

    List<Estudiante> listar();

    Optional<Estudiante> buscarPorId(int id);

    boolean actualizar(Estudiante estudiante);

    boolean eliminar(int id);




}
