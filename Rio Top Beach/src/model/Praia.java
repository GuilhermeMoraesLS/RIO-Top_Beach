package model;

import java.util.ArrayList;
import java.util.List;

public class Praia implements Avaliavel {
    private int id;
    private String nome;
    private String bairro;
    private List<Avaliacao> avaliacoes;

    public Praia(int id, String nome, String bairro) {
        this.id = id;
        this.nome = nome;
        this.bairro = bairro;
        this.avaliacoes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    @Override
    public void adicionarAvaliacao(Avaliacao a) {
        this.avaliacoes.add(a);
    }

    @Override
    public List<String> getComentarios() {
        List<String> comentarios = new ArrayList<>();
        for (Avaliacao a : avaliacoes) {
            comentarios.add(a.getComentario());
        }
        return comentarios;
    }

    public double calcularNotaMedia() {
        if (avaliacoes.isEmpty()) return 0.0;
        double soma = 0.0;
        for (Avaliacao a : avaliacoes) {
            soma += a.getNota();
        }
        return soma / avaliacoes.size();
    }

    public void listarComentarios() {
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhum comentário disponível.");
        } else {
            for (Avaliacao a : avaliacoes) {
                System.out.println(a.getComentario());
            }
        }
    }

    public boolean temNome(String nome) {
        return this.nome != null && this.nome.equalsIgnoreCase(nome);
    }

    public void exibirInformacoes() {
        System.out.println("Praia: " + nome);
        System.out.println("Bairro: " + bairro);
        System.out.println("Média de Nota: " + calcularNotaMedia());
        System.out.println("Quantidade de Avaliações: " + avaliacoes.size());
    }
}