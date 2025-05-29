package model;

import java.util.ArrayList;
import java.util.List;

public class PraiaCatalogo {
    private static List<Praia> praias = new ArrayList<>();

    public static void adicionarPraia(Praia praia) {
        praias.add(praia);
    }

    public static void listarTodas() {
        if (praias.isEmpty()) {
            System.out.println("Nenhuma praia cadastrada.");
        } else {
            for (Praia praia : praias) {
                praia.exibirInformacoes();
                System.out.println("-------------------");
            }
        }
    }

    public static Praia buscarPorNome(String nome) {
        for (Praia praia : praias) {
            if (praia.temNome(nome)) {
                return praia;
            }
        }
        return null;
    }
}