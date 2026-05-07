package util;

import entities.Fidelidade;
import entities.Plano;
import repositoyDB.FidelidadeDB;
import repositoyDB.PlanoDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MetodosPlano {
    private static Scanner sc;

    public static void cadastrarPlano() {
        System.out.println("==================================================");
        System.out.println("***             CADASTRAR PLANO                ***");
        System.out.println("==================================================");

        System.out.print("***              Código do Plano:              ***\n");
        int codPlano = sc.nextInt();
        sc.nextLine();

        System.out.print("***         Categoria do Plano:                ***\n");
        String categoria = sc.nextLine();
        if (!Validador.campoObrigatorio(categoria)) {
            System.out.println("**   Erro: A categoria é obrigatória!          **");
            return;
        }

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
            if (Validador.campoObrigatorio(beneficio)) {
                beneficios.add(beneficio);
            }
        }

        System.out.print("***            Valor de Pagamento: R$          ***\n");
        double pagamento = sc.nextDouble();
        sc.nextLine();

        int codFidelidade = selecionarFidelidade();
        if (codFidelidade == -1) {
            System.out.println("**  Erro: Nenhuma fidelidade válida selecionada. **");
            return;
        }

        Plano novoPlano = new Plano(codPlano, categoria, valor, beneficios, pagamento, codFidelidade);
        new PlanoDB().inserir(novoPlano);
        System.out.println("***       Plano cadastrado com sucesso!        ***");
    }

    public static void listarPlanos() {
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

    public static void alterarPlano() {
        System.out.println("==================================================");
        System.out.println("***             ALTERAR PLANO                  ***");
        System.out.println("==================================================");

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
        if (!novaCategoria.isEmpty()) plano.setCategoria(novaCategoria);

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
                if (Validador.campoObrigatorio(b)) novosBeneficios.add(b);
            }
            plano.setBeneficios(novosBeneficios);
        }

        System.out.print("*** Alterar fidelidade? (S/N):                 ***\n");
        String alterarFidelidade = sc.nextLine();
        if (alterarFidelidade.equalsIgnoreCase("S")) {
            int novoCodFidelidade = selecionarFidelidade();
            if (novoCodFidelidade != -1) {
                plano.setCodFidelidade(novoCodFidelidade);
            } else {
                System.out.println("** Fidelidade inválida. Mantendo a anterior.   **");
            }
        }

        new PlanoDB().atualizar(plano);
        System.out.println("***       Plano atualizado com sucesso!        ***");
    }

    public static void removerPlano() {
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

    public static int selecionarFidelidade() {
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
