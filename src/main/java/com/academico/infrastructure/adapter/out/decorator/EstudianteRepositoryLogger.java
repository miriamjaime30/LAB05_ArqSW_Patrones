package com.academico.infrastructure.adapter.out.decorator;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;

import java.util.List;
import java.util.Optional;

public class EstudianteRepositoryLogger implements EstudianteRepository {
    private final EstudianteRepository delegado;
    public EstudianteRepositoryLogger(EstudianteRepository delegado){
        this.delegado=delegado;
    }
    @Override
    public void guardar(Estudiante estudiante){
        System.out.println("[LOG] guardar -> "+ estudiante);
        delegado.guardar(estudiante);
        System.out.println("[LOG] guardar: operación completada");
    }
    @Override
    public List<Estudiante> listar(){
        System.out.println("[LOG] listar");
        List<Estudiante> resultado = delegado.listar();
        System.out.println("[LOG] listar: " + resultado.size() + " registro(s)");
        return resultado;
    }
    @Override
    public Optional<Estudiante> buscarPorId(int id){
        System.out.println("[LOG] buscarPorId -> "+ id);
        Optional<Estudiante>resultado =delegado.buscarPorId(id);
        System.out.println("[LOG] buscarPorId: " + (resultado.isPresent()?"encontrado":"no encontrado"));
        return resultado;
    }
    @Override
    public boolean actualizar(Estudiante estudiante){
        System.out.println("[LOG] actualizar -> "+ estudiante);
        boolean resultado=delegado.actualizar(estudiante);
        System.out.println("[LOG] actualizar: "+(resultado ? "exito":"no se encontró al estudiante"));
        return resultado;
    }
    @Override
    public boolean eliminar(int id){
        System.out.println("[LOG] eliminar -> "+ id);
        boolean resultado =delegado.eliminar(id);
        System.out.println("[LOG] eliminar: "+ (resultado ? "exito":"no se encontró al estudiante"));
        return resultado;
    }
}
