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