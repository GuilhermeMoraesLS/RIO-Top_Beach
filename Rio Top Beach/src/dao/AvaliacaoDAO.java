package dao;

import bd.ConnectionFactory;
import model.Avaliacao;
import model.Avaliador;
import model.MinhaAvaliacaoDTO;

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
            AvaliadorDAO avaliadorDAO = new AvaliadorDAO();
            while (rs.next()) {
                Avaliador avaliador = avaliadorDAO.buscarPorId(rs.getInt("avaliador_id"));
                Avaliacao avaliacao = new Avaliacao(
                        rs.getInt("id"),
                        rs.getInt("nota"),
                        rs.getString("comentario"),
                        rs.getDate("data").toLocalDate(),
                        avaliador
                );
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar avaliações por praia: " + e.getMessage());
        }
        return avaliacoes;
    }

    /**
     * Busca todas as avaliações de um avaliador específico.
     * @return Uma lista de DTOs que contêm o objeto Avaliacao e o nome da praia.
     */
    public List<MinhaAvaliacaoDTO> buscarPorAvaliador(int avaliadorId) {
        List<MinhaAvaliacaoDTO> minhasAvaliacoes = new ArrayList<>();
        String sql = "SELECT a.id, a.nota, a.comentario, a.data, a.avaliador_id, p.nome as nome_praia " +
                "FROM avaliacao a JOIN praia p ON a.praia_id = p.id " +
                "WHERE a.avaliador_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, avaliadorId);
            ResultSet rs = stmt.executeQuery();

            AvaliadorDAO avaliadorDAO = new AvaliadorDAO();
            Avaliador avaliador = avaliadorDAO.buscarPorId(avaliadorId);

            while(rs.next()) {
                Avaliacao avaliacao = new Avaliacao(
                        rs.getInt("id"),
                        rs.getInt("nota"),
                        rs.getString("comentario"),
                        rs.getDate("data").toLocalDate(),
                        avaliador
                );
                minhasAvaliacoes.add(new MinhaAvaliacaoDTO(avaliacao, rs.getString("nome_praia")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar avaliações do usuário: " + e.getMessage());
        }
        return minhasAvaliacoes;
    }

    /**
     * Atualiza uma avaliação existente no banco de dados.
     * Apenas o dono da avaliação pode atualizá-la.
     * @return true se a operação foi bem-sucedida, false caso contrário.
     */
    public boolean atualizar(int avaliacaoId, int novaNota, String novoComentario, int avaliadorId) {
        String sql = "UPDATE avaliacao SET nota = ?, comentario = ? WHERE id = ? AND avaliador_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaNota);
            stmt.setString(2, novoComentario);
            stmt.setInt(3, avaliacaoId);
            stmt.setInt(4, avaliadorId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar avaliação: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove uma avaliação do banco de dados.
     * Apenas o dono da avaliação pode removê-la.
     * @return true se a operação foi bem-sucedida, false caso contrário.
     */
    public boolean remover(int avaliacaoId, int avaliadorId) {
        String sql = "DELETE FROM avaliacao WHERE id = ? AND avaliador_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, avaliacaoId);
            stmt.setInt(2, avaliadorId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover avaliação: " + e.getMessage());
            return false;
        }
    }
}