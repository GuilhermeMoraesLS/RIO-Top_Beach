package model;

import java.util.List;

public interface Avaliavel {
    void adicionarAvaliacao(Avaliacao a);
    double calcularNotaMedia();
    List<String> getComentarios();
}