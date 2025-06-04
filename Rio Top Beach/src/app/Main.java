package app;

import java.util.Scanner;
import model.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu ===");
            System.out.println("1. Cadastrar nova praia");
            System.out.println("2. Listar todas as praias");
            System.out.println("3. Avaliar uma praia");
            System.out.println("4. Ver detalhes e nota média de uma praia");
            System.out.println("5. Cadastrar avaliador");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Nome da praia: ");
                    String nomePraia = scanner.nextLine();
                    System.out.print("Localização: ");
                    String localizacao = scanner.nextLine();
                    int idPraia = PraiaCatalogo.buscarPorNome(nomePraia) == null ? PraiaCatalogo.class.hashCode() + nomePraia.hashCode() : 0;
                    Praia novaPraia = new Praia(idPraia, nomePraia, localizacao);
                    PraiaCatalogo.adicionarPraia(novaPraia);
                    System.out.println("Praia cadastrada com sucesso!");
                    break;
                case 2:
                    PraiaCatalogo.listarTodas();
                    break;
                case 3:
                    // Listar praias cadastradas
                    java.util.List<Praia> praias = PraiaCatalogo.getPraias();
                    if (praias == null || praias.isEmpty()) {
                        System.out.println("Nenhuma praia cadastrada. Cadastre uma praia antes de avaliar.");
                        break;
                    }
                    System.out.println("Praias cadastradas:");
                    for (int i = 0; i < praias.size(); i++) {
                        System.out.println((i + 1) + ". " + praias.get(i).getNome() + " (" + praias.get(i).getLocalizacao() + ")");
                    }
                    System.out.print("Escolha o número da praia para avaliar: ");
                    int indicePraia = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if (indicePraia < 0 || indicePraia >= praias.size()) {
                        System.out.println("Número de praia inválido.");
                        break;
                    }
                    Praia praiaAvaliar = praias.get(indicePraia);

                    // Listar avaliadores cadastrados
                    java.util.List<Avaliador> avaliadores = AvaliadorCatalogo.getAvaliadores();
                    if (avaliadores == null || avaliadores.isEmpty()) {
                        System.out.println("Nenhum avaliador cadastrado. Cadastre um avaliador antes de avaliar.");
                        break;
                    }
                    System.out.println("Avaliadores cadastrados:");
                    for (int i = 0; i < avaliadores.size(); i++) {
                        System.out.println((i + 1) + ". " + avaliadores.get(i).getNome() + " (" + avaliadores.get(i).getEmail() + ")");
                    }
                    System.out.print("Escolha o número do avaliador: ");
                    int indiceAvaliador = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if (indiceAvaliador < 0 || indiceAvaliador >= avaliadores.size()) {
                        System.out.println("Número de avaliador inválido.");
                        break;
                    }
                    Avaliador avaliador = avaliadores.get(indiceAvaliador);

                    System.out.print("Nota (0-10): ");
                    int nota = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Comentário: ");
                    String comentario = scanner.nextLine();
                    avaliador.avaliarPraia(praiaAvaliar, nota, comentario);
                    System.out.println("Avaliação registrada!");
                    break;
                case 4:
                    System.out.print("Nome da praia: ");
                    String nomeDetalhe = scanner.nextLine();
                    Praia praiaDetalhe = PraiaCatalogo.buscarPorNome(nomeDetalhe);
                    if (praiaDetalhe == null) {
                        System.out.println("Praia não encontrada.");
                    } else {
                        praiaDetalhe.exibirInformacoes();
                        System.out.println("------Comentarios------");
                        praiaDetalhe.listarComentarios();
                        System.out.println("-----------------------");
                    }
                    break;
                case 5:
                    System.out.print("Nome do avaliador: ");
                    String nomeNovoAvaliador = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Tipo de perfil: ");
                    String tipoPerfil = scanner.nextLine();
                    Avaliador novoAvaliador = new Avaliador(1, nomeNovoAvaliador, email, tipoPerfil);
                    AvaliadorCatalogo.adicionarAvaliador(novoAvaliador);
                    System.out.println("Avaliador cadastrado com sucesso!");
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}