package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa uma avaliação feita por um avaliador sobre uma praia.
 */
public class Avaliacao {
    private int id;
    private int nota;
    private String comentario;
    private LocalDate data;
    private Avaliador avaliador;

    /**
     * Construtor da avaliação.
     */
    public Avaliacao(int id, int nota, String comentario, LocalDate data, Avaliador avaliador) {
        this.id = id;
        setNota(nota);
        this.comentario = comentario;
        this.data = data;
        this.avaliador = avaliador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    /**
     * Define a nota da avaliação, entre 0 e 10.
     */
    public void setNota(int nota) {
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10.");
        }
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Avaliador getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Avaliador avaliador) {
        this.avaliador = avaliador;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                ", data=" + data +
                ", avaliador=" + (avaliador != null ? avaliador.getNome() : "N/A") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avaliacao)) return false;
        Avaliacao that = (Avaliacao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}