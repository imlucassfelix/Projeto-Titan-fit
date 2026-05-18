package util;

/**
 * Metodos de negocio para o modulo de Planos do Titan Fit.
 *
 * <p>Gerencia as operacoes de cadastro, listagem, alteracao e
 * remocao de planos.</p>
 *
 * <p>Alteracao v2.0: fidelidade removida do plano. A fidelidade
 * agora e vinculada ao aluno no momento do cadastro, via
 * {@link MetodosAluno#cadastrarAlunos(java.util.Scanner)}.</p>
 *
 * @author Lucas Felix
 * @version 2.0
 * @see interfaceMenu.PlanoMenu
 */

import entities.Plano;
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
            System.out.print("***         Categoria do Plano:                ***\n");
            String categoria = sc.nextLine();
            Validador.campoObrigatorio(categoria, "Categoria");

            System.out.print("***              Valor do Plano: R$            ***\n");
            double valor = sc.nextDouble();
            sc.nextLine();

            System.out.print("***      Quantos beneficios deseja adicionar?  ***\n");
            int qtdBeneficios = sc.nextInt();
            sc.nextLine();

            List<String> beneficios = new ArrayList<>();
            for (int i = 1; i <= qtdBeneficios; i++) {
                System.out.print("***                Beneficio " + i + ":               ***\n");
                String beneficio = sc.nextLine();
                try {
                    Validador.campoObrigatorio(beneficio, "Beneficio " + i);
                    beneficios.add(beneficio);
                } catch (DadoInvalidoExcecao ex) {
                    System.out.println("** Beneficio " + i + " invalido — ignorando: " + ex.getMessage() + " **");
                }
            }

            System.out.print("***         Duracao do Plano (em meses):       ***\n");
            int duracaoMeses = sc.nextInt();
            sc.nextLine();
            if (duracaoMeses <= 0) {
                throw new DadoInvalidoExcecao("Duracao deve ser maior que zero.");
            }

            Plano novoPlano = new Plano(0, categoria, valor, beneficios, duracaoMeses);
            new PlanoDB().inserir(novoPlano);
            System.out.println("***       Plano cadastrado com sucesso!        ***");
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    public static void listarPlanos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***              LISTA DE PLANOS               ***");
        System.out.println("==================================================");

        List<Plano> planos = new PlanoDB().listarTodos();

        if (planos.isEmpty()) {
            System.out.println("***          Nenhum plano cadastrado.          ***");
            return;
        }

        for (Plano p : planos) {
            System.out.println("--------------------------------------------------");
            System.out.println("Codigo:     " + p.getCodPlano());
            System.out.println("Categoria:  " + p.getCategoria());
            System.out.printf("Valor:      R$ %.2f/mes%n", p.getValor());
            System.out.println("Duracao:    " + p.getDuracaoMeses() + " meses");
            System.out.println("Beneficios: " + String.join(", ", p.getBeneficios()));
        }
        System.out.println("==================================================");
    }

    public static void alterarPlano(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             ALTERAR PLANO                  ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Codigo do plano que deseja alterar:        ***\n");
            int codPlano = sc.nextInt();
            sc.nextLine();

            Plano plano = new PlanoDB().buscarPorId(codPlano);
            if (plano == null) {
                System.out.println("*** Erro: Plano com codigo " + codPlano + " nao encontrado. ***");
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

            System.out.print("*** Alterar beneficios? (S/N):                 ***\n");
            String alterarBeneficios = sc.nextLine();
            if (alterarBeneficios.equalsIgnoreCase("S")) {
                System.out.print("*** Quantos beneficios deseja adicionar?       ***\n");
                int qtd = sc.nextInt();
                sc.nextLine();
                List<String> novosBeneficios = new ArrayList<>();
                for (int i = 1; i <= qtd; i++) {
                    System.out.print("*** Beneficio " + i + ":                            ***\n");
                    String b = sc.nextLine();
                    try {
                        Validador.campoObrigatorio(b, "Beneficio " + i);
                        novosBeneficios.add(b);
                    } catch (DadoInvalidoExcecao ex) {
                        System.out.println("** Beneficio " + i + " invalido — ignorando: " + ex.getMessage() + " **");
                    }
                }
                plano.setBeneficios(novosBeneficios);
            }

            System.out.print("*** Nova Duracao em meses (0 para manter):     ***\n");
            int novaDuracao = sc.nextInt();
            sc.nextLine();
            if (novaDuracao > 0) plano.setDuracaoMeses(novaDuracao);

            new PlanoDB().atualizar(plano);
            System.out.println("***       Plano atualizado com sucesso!        ***");
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    public static void removerPlano(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             REMOVER PLANO                  ***");
        System.out.println("==================================================");

        System.out.print("*** Codigo do plano que deseja remover:        ***\n");
        int codPlano = sc.nextInt();
        sc.nextLine();

        Plano plano = new PlanoDB().buscarPorId(codPlano);
        if (plano == null) {
            System.out.println("*** Erro: Plano com codigo " + codPlano + " nao encontrado. ***");
            return;
        }

        System.out.printf("Plano encontrado: %s — R$ %.2f/mes%n",
                plano.getCategoria(), plano.getValor());
        System.out.print("*** Confirma a remocao? (S/N):                 ***\n");
        String confirmacao = sc.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            new PlanoDB().deletar(codPlano);
        } else {
            System.out.println("***           Remocao cancelada.               ***");
        }
    }
}