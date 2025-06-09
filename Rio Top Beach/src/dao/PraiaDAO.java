package dao;

import bd.ConnectionFactory;
import model.Avaliacao;
import model.Praia;
import model.PraiaRankingDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PraiaDAO {

    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    public void inserir(Praia praia) {
        String sql = "INSERT INTO praia (nome, localizacao) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, praia.getNome());
            stmt.setString(2, praia.getLocalizacao());
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
                        rs.getString("localizacao")
                );
                // Carrega as avaliações da praia
                List<Avaliacao> avaliacoes = avaliacaoDAO.buscarPorPraia(praia.getId());
                for(Avaliacao a : avaliacoes) {
                    praia.adicionarAvaliacao(a);
                }
                praias.add(praia);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar praias: " + e.getMessage());
        }
        return praias;
    }

    public Praia buscarPorNome(String nome) {
        String sql = "SELECT * FROM praia WHERE nome = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Praia praia = new Praia(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("localizacao")
                );
                // Carrega as avaliações da praia
                List<Avaliacao> avaliacoes = avaliacaoDAO.buscarPorPraia(praia.getId());
                for(Avaliacao a : avaliacoes) {
                    praia.adicionarAvaliacao(a);
                }
                return praia;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar praia por nome: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca o ranking de praias com base na média das notas das avaliações.
     * @return Uma lista de DTOs com o nome da praia e sua nota média.
     */
    public List<PraiaRankingDTO> getRankingPraias() {
        List<PraiaRankingDTO> ranking = new ArrayList<>();
        String sql = "SELECT p.nome, AVG(a.nota) as nota_media " +
                "FROM praia p " +
                "JOIN avaliacao a ON p.id = a.praia_id " +
                "GROUP BY p.id, p.nome " +
                "ORDER BY nota_media DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nomePraia = rs.getString("nome");
                double notaMedia = rs.getDouble("nota_media");
                ranking.add(new PraiaRankingDTO(nomePraia, notaMedia));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao gerar ranking de praias: " + e.getMessage());
        }
        return ranking;
    }

    public void atualizar(Praia praia) {
        String sql = "UPDATE praia SET nome = ?, localizacao = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, praia.getNome());
            stmt.setString(2, praia.getLocalizacao());
            stmt.setInt(3, praia.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar praia: " + e.getMessage());
        }
    }
}