package dao;

import bd.ConnectionFactory;
import model.Avaliador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvaliadorDAO {

    /**
     * Insere um novo avaliador no banco de dados.
     * Usado para o cadastro de novos usuários.
     */
    public void cadastrarAvaliador(Avaliador avaliador) throws SQLException {
        String sql = "INSERT INTO avaliador (nome, email, senha, tipo_perfil) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, avaliador.getNome());
            stmt.setString(2, avaliador.getEmail());
            stmt.setString(3, avaliador.getSenha());
            stmt.setString(4, avaliador.getTipoPerfil());
            stmt.executeUpdate();
        }
    }

    /**
     * Busca um avaliador pelo email.
     * Usado para verificar se um email já existe no cadastro.
     */
    public Avaliador buscarPorEmail(String email) {
        String sql = "SELECT * FROM avaliador WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Avaliador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("tipo_perfil")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar avaliador por email: " + e.getMessage());
        }
        return null;
    }
}