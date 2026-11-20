package br.com.pizzaria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String URL_PADRAO =
            "jdbc:postgresql://localhost:5432/pizzaria";

    public static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault("DB_URL", URL_PADRAO);
        String usuario = System.getenv().getOrDefault("DB_USER", "postgres");
        String senha = System.getenv().getOrDefault("DB_PASSWORD", "");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL não encontrado. Confira as dependências do Maven.", e);
        }

        return DriverManager.getConnection(url, usuario, senha);
    }
}
