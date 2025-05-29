package model;

import java.util.ArrayList;
import java.util.List;

public class AvaliadorCatalogo {
    private static List<Avaliador> avaliadores = new ArrayList<>();

    public static void adicionarAvaliador(Avaliador avaliador) {
        avaliadores.add(avaliador);
    }

    public static Avaliador buscarPorNome(String nome) {
        for (Avaliador a : avaliadores) {
            if (a.getNome().equalsIgnoreCase(nome)) {
                return a;
            }
        }
        return null;
    }

    public static Avaliador buscarPorEmail(String email) {
        for (Avaliador a : avaliadores) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                return a;
            }
        }
        return null;
    }

    public static void listarTodos() {
        if (avaliadores.isEmpty()) {
            System.out.println("Nenhum avaliador cadastrado.");
        } else {
            for (Avaliador a : avaliadores) {
                a.exibirPerfil();
                System.out.println("-------------------");
            }
        }
    }
}