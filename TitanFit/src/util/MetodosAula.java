package util;

/**
 * Metodos de negocio para o modulo de Aulas do Titan Fit.
 *
 * <p>Gerencia as operacoes de cadastro, listagem, edicao e
 * exclusao de aulas, com chamadas a {@link repositoryDB.AulaDB}.</p>
 *
 * <p>Alteracao v2.0: o campo cod_aula foi removido do formulario
 * de cadastro. O codigo e gerado automaticamente por
 * {@link repositoryDB.AulaDB#proximoCodigo()}.</p>
 *
 * @author Ryan Vinicius
 * @version 2.0
 * @see interfaceMenu.AulaMenu
 */

import entities.Aula;
import java.util.Scanner;

public class MetodosAula {

    public static void cadastrarAula(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***           CADASTRAR AULA                   ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Digite o nome da aula:                     ***\n");
            String nomeAula = sc.nextLine();
            Validador.campoObrigatorio(nomeAula, "Nome da aula");

            System.out.print("*** Modalidade:                                ***\n");
            String modalidade = sc.nextLine();
            Validador.campoObrigatorio(modalidade, "Modalidade");

            System.out.print("*** Descricao da Aula:                         ***\n");
            String descricaoAula = sc.nextLine();
            Validador.campoObrigatorio(descricaoAula, "Descricao da aula");

            System.out.print("*** Capacidade Maxima:                         ***\n");
            int capacidadeMaxima = sc.nextInt(); sc.nextLine();
            Validador.validarCapacidade(capacidadeMaxima);

            System.out.print("*** CPF do Instrutor responsavel:              ***\n");
            String cpfInstrutor = sc.nextLine();
            Validador.validarCpf(cpfInstrutor);

            // cod_aula = 0 aqui — sera sobrescrito por proximoCodigo() dentro de AulaDB.inserir()
            Aula novaAula = new Aula(0, nomeAula, capacidadeMaxima, descricaoAula, modalidade, cpfInstrutor);
            new repositoryDB.AulaDB().inserir(novaAula);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    public static void listarAula(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***           LISTA DE AULAS                   ***");
        System.out.println("==================================================");

        var lista = new repositoryDB.AulaDB().listarTodos();
        if (lista.isEmpty()) {
            System.out.println("***       Nenhuma aula cadastrada.             ***");
        } else {
            for (Aula a : lista) {
                System.out.println("Codigo: " + a.getCodAula());
                System.out.println("Nome da Aula: " + a.getNomeAula());
                System.out.println("Modalidade: " + a.getModalidade());
                System.out.println("Capacidade Maxima: " + a.getCapacidadeMaxima());
                System.out.println("CPF Instrutor: " + a.getCpfInstrutor());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void editarAula(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             EDITAR AULA                    ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Codigo da Aula para editar:       ***\n");
        int codBusca = sc.nextInt(); sc.nextLine();

        repositoryDB.AulaDB db = new repositoryDB.AulaDB();
        Aula aula = db.buscarPorId(codBusca);

        if (aula == null) {
            System.out.println("Erro: Aula com codigo " + codBusca + " nao encontrada!");
            return;
        }

        System.out.println("Editando Aula: " + aula.getNomeAula());

        System.out.print("*** Novo Nome (Enter para manter):             ***\n");
        String novoNome = sc.nextLine();
        if (!novoNome.isEmpty()) aula.setNomeAula(novoNome);

        System.out.print("*** Nova Modalidade (Enter para manter):       ***\n");
        String novaMod = sc.nextLine();
        if (!novaMod.isEmpty()) aula.setModalidade(novaMod);

        db.atualizar(aula);
    }

    public static void excluirAula(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             EXCLUIR AULA                   ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Codigo da Aula para remover:      ***\n");
        int codRemover = sc.nextInt(); sc.nextLine();

        new repositoryDB.AulaDB().deletar(codRemover);
    }
}