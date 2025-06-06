package app;

import dao.AvaliadorDAO;
import dao.PraiaDAO;
import model.Avaliador;
import model.Praia;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AvaliadorDAO avaliadorDAO = new AvaliadorDAO();
    private static final PraiaDAO praiaDAO = new PraiaDAO();
    private static Avaliador usuarioLogado = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== BEM-VINDO AO RIO TOP BEACH ===");
            if (usuarioLogado == null) {
                System.out.println("1. Login");
                System.out.println("2. Cadastrar novo avaliador");
                System.out.println("0. Sair");
            } else {
                exibirMenuLogado();
            }

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (usuarioLogado == null) {
                switch (opcao) {
                    case 1:
                        realizarLogin();
                        break;
                    case 2:
                        realizarCadastro();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        }
    }

    private static void realizarLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Avaliador avaliador = avaliadorDAO.buscarPorEmail(email);

        if (avaliador != null && avaliador.getSenha().equals(senha)) {
            usuarioLogado = avaliador;
            System.out.println("Login realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getNome() + "!");
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private static void realizarCadastro() {
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (avaliadorDAO.buscarPorEmail(email) != null) {
            System.out.println("Erro: Este email já está cadastrado.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Tipo de Perfil (Ex: Turista, Morador): ");
        String tipoPerfil = scanner.nextLine();

        Avaliador novoAvaliador = new Avaliador(0, nome, email, senha, tipoPerfil);

        try {
            avaliadorDAO.cadastrarAvaliador(novoAvaliador);
            System.out.println("Cadastro realizado com sucesso! Você já pode fazer o login.");
        } catch (SQLException e) {
            System.out.println("Erro ao realizar cadastro: " + e.getMessage());
        }
    }

    private static void exibirMenuLogado() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Cadastrar nova praia");
        System.out.println("2. Listar todas as praias");
        System.out.println("3. Avaliar uma praia");
        System.out.println("4. Ver detalhes e nota média de uma praia");
        System.out.println("9. Logout");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        switch (opcao) {
            case 1:
                cadastrarPraia();
                break;
            case 2:
                listarPraias();
                break;
            case 3:
                avaliarPraia();
                break;
            case 4:
                verDetalhesPraia();
                break;
            case 9:
                usuarioLogado = null;
                System.out.println("Logout realizado com sucesso.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void cadastrarPraia() {
        System.out.print("Nome da praia: ");
        String nomePraia = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        // A geração de ID deve ser preferencialmente AUTO_INCREMENT no banco
        int idPraia = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        Praia novaPraia = new Praia(idPraia, nomePraia, bairro);
        praiaDAO.inserir(novaPraia);
        System.out.println("Praia cadastrada com sucesso!");
    }

    private static void listarPraias() {
        List<Praia> praias = praiaDAO.listar();
        if (praias.isEmpty()) {
            System.out.println("Nenhuma praia cadastrada.");
        } else {
            System.out.println("\n--- LISTA DE PRAIAS ---");
            for (Praia praia : praias) {
                praia.exibirInformacoes();
                System.out.println("-------------------");
            }
        }
    }

    private static void avaliarPraia() {
        List<Praia> praias = praiaDAO.listar();
        if (praias.isEmpty()) {
            System.out.println("Nenhuma praia cadastrada para avaliar.");
            return;
        }

        for (int i = 0; i < praias.size(); i++) {
            System.out.println((i + 1) + ". " + praias.get(i).getNome());
        }
        System.out.print("Escolha o número da praia para avaliar: ");
        int indicePraia = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indicePraia < 0 || indicePraia >= praias.size()) {
            System.out.println("Número de praia inválido.");
            return;
        }
        Praia praiaAvaliar = praias.get(indicePraia);

        System.out.print("Nota (0-10): ");
        int nota = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Comentário: ");
        String comentario = scanner.nextLine();

        // Usa o usuário logado para fazer a avaliação
        usuarioLogado.avaliarPraia(praiaAvaliar, nota, comentario);
        // Aqui você precisaria de um AvaliacaoDAO para persistir a avaliação no banco
        System.out.println("Avaliação registrada com sucesso!");
    }

    private static void verDetalhesPraia() {
        System.out.print("Nome da praia para ver detalhes: ");
        String nomeDetalhe = scanner.nextLine();
        // Esta busca ainda está em memória, o ideal seria um praiaDAO.buscarPorNome()
        List<Praia> praias = praiaDAO.listar();
        Praia praiaDetalhe = null;
        for(Praia p : praias) {
            if(p.getNome().equalsIgnoreCase(nomeDetalhe)) {
                praiaDetalhe = p;
                break;
            }
        }

        if (praiaDetalhe == null) {
            System.out.println("Praia não encontrada.");
        } else {
            praiaDetalhe.exibirInformacoes();
            System.out.println("------Comentários------");
            praiaDetalhe.listarComentarios(); // Comentários ainda não são persistidos
            System.out.println("-----------------------");
        }
    }
}