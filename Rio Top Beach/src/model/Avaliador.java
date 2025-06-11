package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Avaliador extends Usuario {
    private String tipoPerfil;
    private List<Avaliacao> avaliacoes;

    public Avaliador(int id, String nome, String email, String senha, String tipoPerfil) {
        super(id, nome, email, senha);
        this.tipoPerfil = tipoPerfil;
        this.avaliacoes = new ArrayList<>();
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
    }

    public void listarAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliação cadastrada.");
        } else {
            for (Avaliacao a : avaliacoes) {
                System.out.println(a);
            }
        }
    }

    public Avaliacao avaliarPraia(Praia praia, int nota, String comentario) {
        int avaliacaoId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE); // Gera um ID único simples
        Avaliacao avaliacao = new Avaliacao(avaliacaoId, nota, comentario, LocalDate.now(), this);
        praia.adicionarAvaliacao(avaliacao);
        this.adicionarAvaliacao(avaliacao);
        System.out.println("Avaliação adicionada à praia: " + praia.getNome() + " | Total de avaliações: " + praia.getAvaliacoes().size());
        return avaliacao;
    }

    @Override
    public void exibirPerfil() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Tipo de Perfil: " + tipoPerfil);
        System.out.println("Quantidade de Avaliações: " + avaliacoes.size());
    }
}