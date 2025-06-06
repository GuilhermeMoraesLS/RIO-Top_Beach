package dao;

import bd.ConnectionFactory;
import model.Avaliacao;
import model.Avaliador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    /**
     * Insere uma nova avaliação no banco de dados, associando-a a uma praia e a um avaliador.
     */
    public void inserir(Avaliacao avaliacao, int praiaId) throws SQLException {
        String sql = "INSERT INTO avaliacao (id, nota, comentario, data, avaliador_id, praia_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, avaliacao.getId());
            stmt.setInt(2, avaliacao.getNota());
            stmt.setString(3, avaliacao.getComentario());
            stmt.setDate(4, Date.valueOf(avaliacao.getData()));
            stmt.setInt(5, avaliacao.getAvaliador().getId());
            stmt.setInt(6, praiaId);
            stmt.executeUpdate();
        }
    }

    /**
     * Busca todas as avaliações associadas a uma praia específica.
     */
    public List<Avaliacao> buscarPorPraia(int praiaId) {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacao WHERE praia_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, praiaId);
            ResultSet rs = stmt.executeQuery();
            Avaliador