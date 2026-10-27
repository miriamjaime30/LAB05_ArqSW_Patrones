package com.academico.application.service;
import com.academico.application.observer.EstudianteObserver;
import com.academico.application.observer.TipoEvento;
import com.academico.application.port.in.EstudianteUseCase;
import com.academico.domain.model.Estudiante;
//import com.academico.infrastructure.persistence.EstudianteRepositoryJson;
import com.academico.domain.repository.EstudianteRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstudianteService implements EstudianteUseCase {

    private final EstudianteRepository repository;
    private final List<EstudianteObserver> observadores=new ArrayList<>();

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }
    public void suscribir(EstudianteObserver observador){
        observadores.add(observador);
    }
    public void desuscribir(EstudianteObserver observador){
        observadores.remove(observador);
    }
    private void notificarObservadores(TipoEvento tipo, Estudiante estudiante){
        for(EstudianteObserver observador:observadores){
            observador.notificar(tipo, estudiante);
        }
    }

    @Override
    public void registrar(Estudiante estudiante) {
        repository.guardar(estudiante);
        notificarObservadores(TipoEvento.REGISTRADO, estudiante);
    }

    @Override
    public List<Estudiante> listar() {
        return repository.listar();
    }

    @Override
    public boolean actualizar(Estudiante estudiante) {
        boolean actualizado= repository.actualizar(estudiante);
        if (actualizado) {
            notificarObservadores(TipoEvento.ACTUALIZADO, estudiante);
        }
        return actualizado;
    }

    @Override
    public boolean eliminar(int id) {
        Optional<Estudiante>existente= repository.buscarPorId(id);
        boolean eliminado= repository.eliminar(id);
        if(eliminado){
            existente.ifPresent(e->notificarObservadores(TipoEvento.ELIMINADO, e));
        }
        return eliminado;
    }

}
