package model;

/**
 * DTO que usa COMPOSIÇÃO. Ele "tem-uma" Avaliacao e adiciona
 * informações contextuais, como o nome da praia, necessárias para a view.
 */
public class MinhaAvaliacaoDTO {
    private final Avaliacao avaliacao; // <-- COMPOSIÇÃO: Contém o objeto original
    private final String nomePraia;

    public MinhaAvaliacaoDTO(Avaliacao avaliacao, String nomePraia) {
        this.avaliacao = avaliacao;
        this.nomePraia = nomePraia;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public String getNomePraia() {
        return nomePraia;
    }
}