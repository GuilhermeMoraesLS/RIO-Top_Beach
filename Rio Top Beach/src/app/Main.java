package app;

import dao.AvaliadorDAO;
import dao.AvaliacaoDAO;
import dao.PraiaDAO;
import model.Avaliador;
import model.Avaliacao;
import model.Praia;
import model.MinhaAvaliacaoDTO;
import model.PraiaRankingDTO;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AvaliadorDAO avaliadorDAO = new AvaliadorDAO();
    private static final PraiaDAO praiaDAO = new PraiaDAO();
    private static final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private static Avaliador usuarioLogado = null;

    public static void main(String[] args) {
        while (true) {
            if (usuarioLogado == null) {
                exibirMenuDeslogado();
            } else {
                exibirMenuLogado();
            }
        }
    }

    private static void exibirMenuDeslogado() {
        System.out.println("\n🌊 === BEM-VINDO AO RIO TOP BEACH === 🌊");
        System.out.println("==========================================");
        System.out.println("1. Login");
        System.out.println("2. Cadastrar novo avaliador");
        System.out.println("0. Sair");
        System.out.println("==========================================");
        System.out.print("Escolha uma opção: ");
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    realizarLogin();
                    break;
                case 2:
                    realizarCadastro();
                    break;
                case 0:
                    System.out.println("\nSaindo do sistema... Até a próxima!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nOpção inválida. Por favor, insira um número.");
            scanner.nextLine();
        }
    }

    private static void realizarLogin() {
        System.out.println("\n--- LOGIN DE AVALIADOR ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Avaliador avaliador = avaliadorDAO.buscarPorEmail(email);

        if (avaliador != null && avaliador.getSenha().equals(senha)) {
            usuarioLogado = avaliador;
            System.out.println("\n✅ Login realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getNome() + "!");
        } else {
            System.out.println("\n❌ Email ou senha inválidos.");
        }
    }

    private static void realizarCadastro() {
        System.out.println("\n--- CADASTRO DE NOVO AVALIADOR ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (avaliadorDAO.buscarPorEmail(email) != null) {
            System.out.println("\n❌ Erro: Este email já está cadastrado.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Tipo de Perfil (Ex: Turista, Morador): ");
        String tipoPerfil = scanner.nextLine();

        Avaliador novoAvaliador = new Avaliador(0, nome, email, senha, tipoPerfil);

        try {
            avaliadorDAO.cadastrarAvaliador(novoAvaliador);
            System.out.println("\n✅ Cadastro realizado com sucesso! Você já pode fazer o login.");
        } catch (SQLException e) {
            System.out.println("\n❌ Erro ao realizar cadastro: " + e.getMessage());
        }
    }

    private static void exibirMenuLogado() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("Olá, " + usuarioLogado.getNome() + "!");
        System.out.println("---------------------------------");
        System.out.println("1. Cadastrar nova praia");
        System.out.println("2. Listar todas as praias");
        System.out.println("3. Avaliar uma praia");
        System.out.println("4. Ver detalhes de uma praia");
        System.out.println("5. Ver ranking das praias");
        System.out.println("6. Minhas Avaliações");
        System.out.println("9. Logout");
        System.out.println("---------------------------------");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrarPraia(); break;
                case 2: listarPraias(); break;
                case 3: avaliarPraia(); break;
                case 4: verDetalhesPraia(); break;
                case 5: exibirRankingPraias(); break;
                case 6: gerenciarMinhasAvaliacoes(); break;
                case 9:
                    usuarioLogado = null;
                    System.out.println("\nLogout realizado com sucesso.");
                    break;
                default:
                    System.out.println("\nOpção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nOpção inválida. Por favor, insira um número.");
            scanner.nextLine();
        }
    }

    private static void cadastrarPraia() {
        System.out.println("\n--- CADASTRAR NOVA PRAIA ---");
        System.out.print("Nome da praia: ");
        String nomePraia = scanner.nextLine();
        System.out.print("Bairro/Localização: ");
        String bairro = scanner.nextLine();

        Praia novaPraia = new Praia(0, nomePraia, bairro);
        praiaDAO.inserir(novaPraia);
        System.out.println("\n✅ Praia cadastrada com sucesso!");
    }

    private static void listarPraias() {
        System.out.println("\n--- LISTA DE PRAIAS CADASTRADAS ---");
        List<Praia> praias = praiaDAO.listar();
        if (praias.isEmpty()) {
            System.out.println("Nenhuma praia cadastrada.");
        } else {
            for (Praia praia : praias) {
                praia.exibirInformacoes();
                System.out.println("-------------------");
            }
        }
    }

    private static void avaliarPraia() {
        System.out.println("\n--- AVALIAR UMA PRAIA ---");
        List<Praia> praias = praiaDAO.listar();
        if (praias.isEmpty()) {
            System.out.println("Nenhuma praia cadastrada para avaliar.");
            return;
        }

        for (int i = 0; i < praias.size(); i++) {
            System.out.println((i + 1) + ". " + praias.get(i).getNome());
        }
        System.out.print("Escolha o número da praia para avaliar: ");
        try {
            int indicePraia = scanner.nextInt() - 1;
            scanner.nextLine();

            if (indicePraia < 0 || indicePraia >= praias.size()) {
                System.out.println("\n❌ Número de praia inválido.");
                return;
            }
            Praia praiaAvaliar = praias.get(indicePraia);

            System.out.print("Nota (0-10): ");
            int nota = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Comentário: ");
            String comentario = scanner.nextLine();

            Avaliacao novaAvaliacao = usuarioLogado.avaliarPraia(praiaAvaliar, nota, comentario);
            avaliacaoDAO.inserir(novaAvaliacao, praiaAvaliar.getId());
            System.out.println("\n✅ Avaliação registrada com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("❌ Entrada inválida. Por favor, insira um número.");
            scanner.nextLine();
        } catch (SQLException e) {
            System.out.println("\n❌ Erro ao salvar avaliação: " + e.getMessage());
        }
    }

    private static void verDetalhesPraia() {
        System.out.println("\n--- DETALHES DA PRAIA ---");
        System.out.print("Nome da praia para ver detalhes: ");
        String nomeDetalhe = scanner.nextLine();

        Praia praiaDetalhe = praiaDAO.buscarPorNome(nomeDetalhe);

        if (praiaDetalhe == null) {
            System.out.println("\n❌ Praia não encontrada.");
        } else {
            System.out.println("\n-----------------------");
            praiaDetalhe.exibirInformacoes();
            System.out.println("------ Comentários ------");
            praiaDetalhe.listarComentarios();
            System.out.println("-----------------------");
        }
    }

    private static void exibirRankingPraias() {
        System.out.println("\n🏆 --- RANKING RIO TOP BEACH --- 🏆");
        System.out.println("====================================");
        List<PraiaRankingDTO> ranking = praiaDAO.getRankingPraias();

        if (ranking.isEmpty()) {
            System.out.println("Ainda não há praias avaliadas para formar um ranking.");
        } else {
            int posicao = 1;
            for (PraiaRankingDTO item : ranking) {
                System.out.printf("%dº. %-20s | Nota Média: %.1f%n",
                        posicao++,
                        item.getNome(),
                        item.getNotaMedia());
            }
        }
        System.out.println("====================================");
    }

    private static void gerenciarMinhasAvaliacoes() {
        while (true) {
            System.out.println("\n--- MINHAS AVALIAÇÕES ---");
            System.out.println("1. Listar minhas avaliações");
            System.out.println("2. Editar uma avaliação");
            System.out.println("3. Apagar uma avaliação");
            System.out.println("4. Voltar ao menu principal");
            System.out.println("---------------------------");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1: listarMinhasAvaliacoes(); break;
                    case 2: editarMinhaAvaliacao(); break;
                    case 3: apagarMinhaAvaliacao(); break;
                    case 4: return;
                    default: System.out.println("\nOpção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nOpção inválida. Por favor, insira um número.");
                scanner.nextLine();
            }
        }
    }

    private static List<MinhaAvaliacaoDTO> listarMinhasAvaliacoes() {
        System.out.println("\n--- SUAS AVALIAÇÕES REGISTRADAS ---");
        List<MinhaAvaliacaoDTO> minhasAvaliacoes = avaliacaoDAO.buscarPorAvaliador(usuarioLogado.getId());

        if (minhasAvaliacoes.isEmpty()) {
            System.out.println("Você ainda não fez nenhuma avaliação.");
        } else {
            for (int i = 0; i < minhasAvaliacoes.size(); i++) {
                MinhaAvaliacaoDTO dto = minhasAvaliacoes.get(i);
                System.out.printf("%d. Praia: %s%n", (i + 1), dto.getNomePraia());
                System.out.printf("   Nota: %d | Data: %s%n", dto.getAvaliacao().getNota(), dto.getAvaliacao().getData());
                System.out.printf("   Comentário: \"%s\"%n", dto.getAvaliacao().getComentario());
                System.out.println("----------------------------------------");
            }
        }
        return minhasAvaliacoes;
    }

    private static void editarMinhaAvaliacao() {
        List<MinhaAvaliacaoDTO> minhasAvaliacoes = listarMinhasAvaliacoes();
        if (minhasAvaliacoes.isEmpty()) return;

        System.out.print("\nDigite o número da avaliação que deseja editar: ");
        try {
            int escolha = scanner.nextInt() - 1;
            scanner.nextLine();

            if (escolha < 0 || escolha >= minhasAvaliacoes.size()) {
                System.out.println("❌ Número inválido.");
                return;
            }

            MinhaAvaliacaoDTO dtoParaEditar = minhasAvaliacoes.get(escolha);

            System.out.printf("Nova nota para a avaliação da praia %s (0-10): ", dtoParaEditar.getNomePraia());
            int novaNota = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Novo comentário: ");
            String novoComentario = scanner.nextLine();

            boolean sucesso = avaliacaoDAO.atualizar(dtoParaEditar.getAvaliacao().getId(), novaNota, novoComentario, usuarioLogado.getId());
            if (sucesso) System.out.println("\n✅ Avaliação atualizada com sucesso!");
            else System.out.println("\n❌ Falha ao atualizar a avaliação.");

        } catch (InputMismatchException e) {
            System.out.println("❌ Entrada inválida. Por favor, insira um número.");
            scanner.nextLine();
        }
    }

    private static void apagarMinhaAvaliacao() {
        List<MinhaAvaliacaoDTO> minhasAvaliacoes = listarMinhasAvaliacoes();
        if (minhasAvaliacoes.isEmpty()) return;

        System.out.print("\nDigite o número da avaliação que deseja APAGAR: ");
        try {
            int escolha = scanner.nextInt() - 1;
            scanner.nextLine();

            if (escolha < 0 || escolha >= minhasAvaliacoes.size()) {
                System.out.println("❌ Número inválido.");
                return;
            }

            MinhaAvaliacaoDTO dtoParaApagar = minhasAvaliacoes.get(escolha);

            System.out.printf("Tem certeza que deseja apagar sua avaliação da praia %s? (S/N): ", dtoParaApagar.getNomePraia());
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                boolean sucesso = avaliacaoDAO.remover(dtoParaApagar.getAvaliacao().getId(), usuarioLogado.getId());
                if (sucesso) System.out.println("\n✅ Avaliação removida com sucesso!");
                else System.out.println("\n❌ Falha ao remover a avaliação.");
            } else {
                System.out.println("\nOperação cancelada.");
            }

        } catch (InputMismatchException e) {
            System.out.println("❌ Entrada inválida. Por favor, insira um número.");
            scanner.nextLine();
        }
    }
}