package dao;

import bd.ConnectionFactory;
import model.Praia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PraiaDAO {

    public void inserir(Praia praia) {
        // Altere a query e o método para "localizacao"
        String sql = "INSERT INTO praia (id, nome, localizacao) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, praia.getId());
            stmt.setString(2, praia.getNome());
            stmt.setString(3, praia.getLocalizacao()); // CORRIGIDO
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir praia: " + e.getMessage());
        }
    }

    public List<Praia> listar() {
        List<Praia> praias = new ArrayList<>();
        String sql = "SELECT * FROM praia";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Praia praia = new Praia(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("localizacao") // CORRIGIDO
                );
                praias.add(praia);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar praias: " + e.getMessage());
        }
        return praias;
    }

    public void atualizar(Praia praia) {
        // Altere a query e o método para "localizacao"
        String sql = "UPDATE praia SET nome = ?, localizacao = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, praia.getNome());
            stmt.setString(2, praia.getLocalizacao()); // CORRIGIDO
            stmt.setInt(3, praia.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar praia: " + e.getMessage());
        }
    }

    // ... o método remover() não precisa de alterações
}