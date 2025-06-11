package model;

/**
 * DTO (Data Transfer Object) para carregar os dados do ranking de praias.
 * Contém apenas os dados necessários para a exibição do ranking.
 */
public class PraiaRankingDTO {
    private String nome;
    private double notaMedia;

    public PraiaRankingDTO(String nome, double notaMedia) {
        this.nome = nome;
        this.notaMedia = notaMedia;
    }

    public String getNome() {
        return nome;
    }

    public double getNotaMedia() {
        return notaMedia;
    }
}