package model;

import java.time.LocalDate;

public class Avaliacao {
    private int id;
    private int nota;
    private String comentario;
    private LocalDate data;
    private Avaliador avaliador;

    public Avaliacao(int id, int nota, String comentario, LocalDate data, Avaliador avaliador) {
        this.id = id;
        this.nota = nota;
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

    public void setNota(int nota) {
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
}