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
                    System.out.print("Nome da praia para avaliar: ");
                    String nomeAvaliar = scanner.nextLine();
                    Praia praiaAvaliar = PraiaCatalogo.buscarPorNome(nomeAvaliar);
                    if (praiaAvaliar == null) {
                        System.out.println("Praia não encontrada.");
                        break;
                    }
                    System.out.print("Nome do avaliador: ");
                    String nomeAvaliador = scanner.nextLine();
                    Avaliador avaliador = AvaliadorCatalogo.buscarPorNome(nomeAvaliador);
                    if (avaliador == null) {
                        System.out.println("Avaliador não encontrado. Cadastre o avaliador primeiro.");
                        break;
                    }
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