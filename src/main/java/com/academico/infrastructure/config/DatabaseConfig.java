package com.academico.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static volatile DatabaseConfig instance;
    private final String url;
    private final String usuario;
    private final String password;
    private DatabaseConfig(){
        this.url=System.getenv().getOrDefault("DB_URL","jdbc:postgresql://localhost:5432/sistema_gestion_academica");
        this.usuario=System.getenv().getOrDefault("DB_USER", "postgres");
        this.password =System.getenv().getOrDefault("DB_PASSWORD","123456");
        System.out.println("[DatabaseConfig] Configuración cargada(una sola vez)");

    }
    public static DatabaseConfig getInstance(){
        if(instance ==null){
            synchronized (DatabaseConfig.class){
                if(instance==null){
                    instance= new DatabaseConfig();
                }
            }
        }
        return instance;
    }
    public String getUrl(){return url;}
    public String getUsuario(){return usuario;}
    public String getPassword(){return password;}
    public Connection getConnection()throws SQLException{
        if(password==null || password.isBlank()){
            throw new IllegalStateException("Configura la variable de entorno DB_PASSWORD");

        }
        return DriverManager.getConnection(url,usuario,password);
    }
}
