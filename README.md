# Laboratorio 05: Implementación de Patrones de Diseño

**Asignatura:** IS488 – Arquitectura de Software | **Semestre:** 2026-II
**Estudiante:** Miriam Reyna Jaime Nuñez
**Sistema:** Gestión Académica (arquitectura hexagonal, Java + Maven)

## Cómo ejecutar

- Requiere el JDK indicado en `pom.xml` y Maven.
- Clase principal: `com.academico.Main`.
- Almacenamiento, según la variable de entorno `STORAGE_TYPE`:
    - `JSON` (por defecto): usa `data/estudiantes.json`.
    - `POSTGRES`: usa `DB_URL`, `DB_USER` y `DB_PASSWORD`.

## Patrones implementados

| Tipo | Patrón | Dónde está en el código |
|---|---|---|
| Creacional | Factory (visto en clase) | `infrastructure.adapter.in.RepositoryFactory` |
| Creacional | Singleton | `infrastructure.config.DatabaseConfig` |
| Estructural | Decorator | `infrastructure.adapter.out.decorator.EstudianteRepositoryLogger` |
| Comportamiento | Observer | `application.observer` e `infrastructure.adapter.out.observer` |
| Adicional | Chain of Responsibility | `application.validation` |
| Adicional | Command | `presentation.command` |


## 1. Factory

`RepositoryFactory.crear()` decide, según la variable `STORAGE_TYPE`, qué implementación de `EstudianteRepository` se crea (JSON o PostgreSQL). El resto del sistema solo conoce la interfaz y no depende de las clases concretas. Es la variante *Simple Factory* (método estático); además, el repositorio creado se entrega envuelto por el Decorator descrito más abajo.

```mermaid
classDiagram
    class RepositoryFactory {
        +crear()$ EstudianteRepository
    }
    class EstudianteRepository {
        <<interface>>
    }
    class EstudianteRepositoryJson
    class EstudianteRepositoryPostgres
    EstudianteRepository <|.. EstudianteRepositoryJson
    EstudianteRepository <|.. EstudianteRepositoryPostgres
    RepositoryFactory ..> EstudianteRepositoryJson : crea
    RepositoryFactory ..> EstudianteRepositoryPostgres : crea
```

## 2. Singleton

`DatabaseConfig` centraliza la configuración de acceso a PostgreSQL. Tiene constructor privado, una única instancia y un punto de acceso global, `getInstance()`, protegido con `volatile` y un bloque `synchronized` para que dos hilos no creen dos instancias. La configuración se carga una sola vez y `EstudianteRepositoryPostgres` la reutiliza.

```mermaid
classDiagram
    class DatabaseConfig {
        -DatabaseConfig instance$
        -String url
        -String usuario
        -String password
        -DatabaseConfig()
        +getInstance()$ DatabaseConfig
        +getConnection() Connection
    }
    EstudianteRepositoryPostgres ..> DatabaseConfig : usa
```

Salida de la demostración en `Main`:

```
[DatabaseConfig] Configuración cargada (una sola vez).
¿Misma instancia? true
```


## 3. Decorator

`EstudianteRepositoryLogger` implementa la misma interfaz que el repositorio que envuelve (`EstudianteRepository`) y registra cada operación antes y después de delegarla. Así se añade una funcionalidad nueva sin modificar `EstudianteRepositoryJson`, `EstudianteRepositoryPostgres` ni `EstudianteService`. `RepositoryFactory` entrega el repositorio ya decorado.

```mermaid
classDiagram
    class EstudianteRepository {
        <<interface>>
        +guardar(Estudiante)
        +listar() List
        +buscarPorId(int) Optional
        +actualizar(Estudiante) boolean
        +eliminar(int) boolean
    }
    class EstudianteRepositoryJson
    class EstudianteRepositoryPostgres
    class EstudianteRepositoryLogger {
        -EstudianteRepository delegado
    }
    EstudianteRepository <|.. EstudianteRepositoryJson
    EstudianteRepository <|.. EstudianteRepositoryPostgres
    EstudianteRepository <|.. EstudianteRepositoryLogger
    EstudianteRepositoryLogger o--> EstudianteRepository : envuelve
```

Salida al listar estudiantes:

```
[LOG] listar
[LOG] listar: 3 registro(s)
```

## 4. Observer

`EstudianteService` actúa como publicador: cuando se registra, actualiza o elimina un estudiante, notifica a todos sus suscriptores. Los observadores concretos son `AuditoriaObserver` (deja constancia con fecha y hora) y `CorreoObserver` (simula el envío de un correo). Se suscriben desde `Main`, y el servicio no conoce sus clases concretas, solo la interfaz `EstudianteObserver`.

```mermaid
classDiagram
    class EstudianteService {
        -List observadores
        +suscribir(EstudianteObserver)
        +desuscribir(EstudianteObserver)
        -notificarObservadores(TipoEvento, Estudiante)
    }
    class EstudianteObserver {
        <<interface>>
        +notificar(TipoEvento, Estudiante)
    }
    class TipoEvento {
        <<enumeration>>
        REGISTRADO
        ACTUALIZADO
        ELIMINADO
    }
    class AuditoriaObserver
    class CorreoObserver
    EstudianteService o--> EstudianteObserver : notifica
    EstudianteObserver <|.. AuditoriaObserver
    EstudianteObserver <|.. CorreoObserver
    EstudianteObserver ..> TipoEvento
```

Salida al eliminar un estudiante (se ven juntos el Decorator y el Observer):

```
[LOG] buscarPorId -> 3
[LOG] buscarPorId: encontrado
[LOG] eliminar -> 3
[LOG] eliminar: éxito
[AUDITORÍA] 27/10/2026 10:22:34 - ELIMINADO - id=3, nombre=Miriam
[CORREO] Para: miriam@unsch.com | Tu registro fue eliminado del sistema.
Estudiante eliminado.
```