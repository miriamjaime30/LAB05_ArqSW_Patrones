package com.academico.infrastructure.adapter.out.persistence;

import com.academico.domain.model.Estudiante;
import com.academico.domain.repository.EstudianteRepository;

import java.sql.*;
import java.util.*;

public class EstudianteRepositoryPostgres implements EstudianteRepository {

    private final String url;
    private final String usuario;
    private final String password;

    public EstudianteRepositoryPostgres() {
        this(
                System.getenv().getOrDefault(
                        "DB_URL",
                        "jdbc:postgresql://localhost:5432/sistema_gestion_academica"
                ),
                System.getenv().getOrDefault("DB_USER", "postgres"),
                System.getenv().getOrDefault("DB_PASSWORD", "123456")
        );
    }

    // Constructor adicional para configurar o probar la conexión.
    public EstudianteRepositoryPostgres(String url, String usuario, String password) {
        this.url = url;
        this.usuario = usuario;
        this.password = password;
    }

    private Connection conectar() throws SQLException {
        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Configura la variable de entorno DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(
                url, usuario, password
        );
    }


    @Override
    public void guardar(Estudiante estudiante) {
        String sql = """
            INSERT INTO estudiantes (id, nombre, correo)
            VALUES (?, ?, ?)
            """;

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estudiante.getId());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo registrar al estudiante.", e
            );
        }
    }

    @Override
    public List<Estudiante> listar() {
        String sql = """
            SELECT id, nombre, correo
            FROM estudiantes
            ORDER BY id
            """;

        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                estudiantes.add(new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo")
                ));
            }

            return estudiantes;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudieron listar los estudiantes.", e
            );
        }
    }

    @Override
    public Optional<Estudiante> buscarPorId(int id) {
        String sql = """
            SELECT id, nombre, correo
            FROM estudiantes
            WHERE id = ?
            """;

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Estudiante(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo")
                    ));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo buscar al estudiante.", e
            );
        }
    }

    @Override
    public boolean actualizar(Estudiante estudiante) {
        String sql = """
            UPDATE estudiantes
            SET nombre = ?, correo = ?
            WHERE id = ?
            """;

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getCorreo());
            ps.setInt(3, estudiante.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo actualizar al estudiante.", e
            );
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo eliminar al estudiante.", e
            );
        }
    }



}
