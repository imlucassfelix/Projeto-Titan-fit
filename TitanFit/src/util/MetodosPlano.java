package util;

/**
 * Metodos de negocio para o modulo de Planos do Titan Fit.
 *
 * <p>Gerencia as operacoes de cadastro, listagem, alteracao e
 * remocao de planos, incluindo a selecao interativa de
 * {@link entities.Fidelidade}.</p>
 *
 * @author Lucas Felix
 * @version 1.0
 * @see interfaceMenu.PlanoMenu
 */

import entities.Fidelidade;
import entities.Plano;
import repositoryDB.FidelidadeDB;
import repositoryDB.PlanoDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MetodosPlano {

    public static void cadastrarPlano(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             CADASTRAR PLANO                ***");
        System.out.println("==================================================");
        try {
            System.out.print("***              Código do Plano:              ***\n");
            int codPlano = sc.nextInt();
            sc.nextLine();

            System.out.print("***         Categoria do Plano:                ***\n");
            String categoria = sc.nextLine();
            Validador.campoObrigatorio(categoria, "Categoria");

            System.out.print("***              Valor do Plano: R$            ***\n");
            double valor = sc.nextDouble();
            sc.nextLine();

            System.out.print("***      Quantos benefícios deseja adicionar?  ***\n");
            int qtdBeneficios = sc.nextInt();
            sc.nextLine();

            List<String> beneficios = new ArrayList<>();
            for (int i = 1; i <= qtdBeneficios; i++) {
                System.out.print("***                Benefício " + i + ":               ***\n");
                String beneficio = sc.nextLine();
                try {
                    Validador.campoObrigatorio(beneficio, "Benefício " + i);
                    beneficios.add(beneficio);
                } catch (DadoInvalidoExcecao ex) {
                    System.out.println("** Benefício " + i + " inválido — ignorando: " + ex.getMessage() + " **");
                }
            }

            System.out.print("***            Valor de Pagamento: R$          ***\n");
            double pagamento = sc.nextDouble();
            sc.nextLine();

            int codFidelidade = selecionarFidelidade(sc);
            if (codFidelidade == -1) {
                System.out.println("**  Erro: Nenhuma fidelidade válida selecionada. **");
                return;
            }

            System.out.print("***         Duração do Plano (em meses):       ***\n");
            int duracaoMeses = sc.nextInt();
            sc.nextLine();
            if (duracaoMeses <= 0) {
                throw new DadoInvalidoExcecao("Duração deve ser maior que zero.");
            }

            Plano novoPlano = new Plano(codPlano, categoria, valor, beneficios, pagamento, codFidelidade, duracaoMeses);
            new PlanoDB().inserir(novoPlano);
            System.out.println("***       Plano cadastrado com sucesso!        ***");
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void listarPlanos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***              LISTA DE PLANOS               ***");
        System.out.println("==================================================");

        List<Plano> planos = new PlanoDB().listarTodos();
        FidelidadeDB fidelidadeDB = new FidelidadeDB();

        if (planos.isEmpty()) {
            System.out.println("***          Nenhum plano cadastrado.          ***");
            return;
        }

        for (Plano p : planos) {
            System.out.println("--------------------------------------------------");
            System.out.println("Código:     " + p.getCodPlano());
            System.out.println("Categoria:  " + p.getCategoria());
            System.out.printf("Valor:      R$ %.2f%n", p.getValor());
            System.out.printf("Pagamento:  R$ %.2f%n", p.getPagamento());
            System.out.println("Benefícios: " + String.join(", ", p.getBeneficios()));
            System.out.println("Duração:    " + p.getDuracaoMeses() + " meses");

            Fidelidade fidelidade = fidelidadeDB.buscarPorId(p.getCodFidelidade());
            if (fidelidade != null) {
                System.out.println("Fidelidade: [" + fidelidade.getCodFidelidade() + "] "
                        + fidelidade.getDescricao() + " — até " + fidelidade.getPeriodo());
            } else {
                System.out.println("Fidelidade: código " + p.getCodFidelidade() + " (não encontrada no banco)");
            }

        }
        System.out.println("==================================================");
    }

    public static void alterarPlano(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             ALTERAR PLANO                  ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Código do plano que deseja alterar:        ***\n");
            int codPlano = sc.nextInt();
            sc.nextLine();

            Plano plano = new PlanoDB().buscarPorId(codPlano);
            if (plano == null) {
                System.out.println("*** Erro: Plano com código " + codPlano + " não encontrado. ***");
                return;
            }

            System.out.println("Editando plano: " + plano.getCategoria());

            System.out.print("*** Nova Categoria (Enter para manter):        ***\n");
            String novaCategoria = sc.nextLine();
            if (!novaCategoria.isEmpty()) {
                Validador.campoObrigatorio(novaCategoria, "Categoria");
                plano.setCategoria(novaCategoria);
            }

            System.out.print("*** Novo Valor (0 para manter): R$             ***\n");
            double novoValor = sc.nextDouble();
            sc.nextLine();
            if (novoValor > 0) plano.setValor(novoValor);

            System.out.print("*** Novo Pagamento (0 para manter): R$         ***\n");
            double novoPagamento = sc.nextDouble();
            sc.nextLine();
            if (novoPagamento > 0) plano.setPagamento(novoPagamento);

            System.out.print("*** Alterar benefícios? (S/N):                 ***\n");
            String alterarBeneficios = sc.nextLine();
            if (alterarBeneficios.equalsIgnoreCase("S")) {
                System.out.print("*** Quantos benefícios deseja adicionar?       ***\n");
                int qtd = sc.nextInt();
                sc.nextLine();
                List<String> novosBeneficios = new ArrayList<>();
                for (int i = 1; i <= qtd; i++) {
                    System.out.print("*** Benefício " + i + ":                            ***\n");
                    String b = sc.nextLine();
                    try {
                        Validador.campoObrigatorio(b, "Benefício " + i);
                        novosBeneficios.add(b);
                    } catch (DadoInvalidoExcecao ex) {
                        System.out.println("** Benefício " + i + " inválido — ignorando: " + ex.getMessage() + " **");
                    }
                }
                plano.setBeneficios(novosBeneficios);
            }

            System.out.print("*** Alterar fidelidade? (S/N):                 ***\n");
            String alterarFidelidade = sc.nextLine();
            if (alterarFidelidade.equalsIgnoreCase("S")) {
                int novoCodFidelidade = selecionarFidelidade(sc);
                if (novoCodFidelidade != -1) {
                    plano.setCodFidelidade(novoCodFidelidade);
                } else {
                    System.out.println("** Fidelidade inválida. Mantendo a anterior.   **");
                }
            }

            System.out.print("*** Nova Duração em meses (0 para manter):     ***\n");
            int novaDuracao = sc.nextInt();
            sc.nextLine();
            if (novaDuracao > 0) plano.setDuracaoMeses(novaDuracao);

            new PlanoDB().atualizar(plano);
            System.out.println("***       Plano atualizado com sucesso!        ***");
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void removerPlano(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             REMOVER PLANO                  ***");
        System.out.println("==================================================");

        System.out.print("*** Código do plano que deseja remover:        ***\n");
        int codPlano = sc.nextInt();
        sc.nextLine();

        Plano plano = new PlanoDB().buscarPorId(codPlano);
        if (plano == null) {
            System.out.println("*** Erro: Plano com código " + codPlano + " não encontrado. ***");
            return;
        }

        System.out.println("Plano encontrado: " + plano.getCategoria() + " — R$ " + plano.getValor());
        System.out.print("*** Confirma a remoção? (S/N):                 ***\n");
        String confirmacao = sc.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            new PlanoDB().deletar(codPlano);
        } else {
            System.out.println("***           Remoção cancelada.               ***");
        }
    }

    public static int selecionarFidelidade(Scanner sc) {
        FidelidadeDB fidelidadeDB = new FidelidadeDB();
        List<Fidelidade> fidelidades = fidelidadeDB.listarTodos();

        if (fidelidades.isEmpty()) {
            System.out.println("** Aviso: Nenhuma fidelidade cadastrada no sistema. **");
            System.out.println("** Cadastre uma fidelidade antes de criar um plano. **");
            return -1;
        }

        System.out.println("==================================================");
        System.out.println("***         FIDELIDADES DISPONÍVEIS            ***");
        System.out.println("==================================================");
        for (Fidelidade f : fidelidades) {
            System.out.println("[" + f.getCodFidelidade() + "] " + f.getDescricao()
                    + " — Período até: " + f.getPeriodo());
        }
        System.out.println("==================================================");

        System.out.print("***    Digite o Código de Fidelidade:          ***\n");
        int codSelecionado = sc.nextInt();
        sc.nextLine();

        boolean encontrado = fidelidades.stream()
                .anyMatch(f -> f.getCodFidelidade() == codSelecionado);

        if (!encontrado) {
            System.out.println("** Erro: Código de fidelidade inexistente!     **");
            return -1;
        }

        return codSelecionado;
    }
}