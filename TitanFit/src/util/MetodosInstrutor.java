package util;

/**
 * Metodos de negocio para o modulo de Instrutores do Titan Fit.
 *
 * <p>Encapsula as operacoes de cadastro, listagem, atualizacao
 * e remocao de instrutores, com validacoes e acesso a
 * {@link repositoryDB.InstrutorDB}.</p>
 *
 * @author Mateus Santos
 * @version 1.0
 * @see interfaceMenu.InstrutorMenu
 */

import entities.Instrutor;
import repositoryDB.InstrutorDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MetodosInstrutor {

    public static void cadastrarInstrutor(Scanner sc){
        System.out.println("==================================================");
        System.out.println("*** CADASTRAR INSTRUTOR               ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** CPF do Instrutor:              ***\n");
            String cpfInstrutor = sc.nextLine();
            Validador.validarCpf(cpfInstrutor);

            System.out.print("*** Nome completo:               ***\n");
            String nomeInstrutor = sc.nextLine();
            Validador.campoObrigatorio(nomeInstrutor, "Nome");

            System.out.print("*** Email:                   ***\n");
            String emailInstrutor = sc.nextLine();
            Validador.validarEmail(emailInstrutor);

            System.out.print("*** Data de nascimento (dd/mm/aaaa):  ***\n");
            String dataNascStr = sc.nextLine();
            Validador.validarData(dataNascStr);
            LocalDate dataNascimento = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("*** Sexo (M/F/I):               ***\n");
            String sexo = sc.nextLine();
            Validador.validarSexo(sexo);

            System.out.print("*** Especialidade:               ***\n");
            String especialidade = sc.nextLine();
            Validador.campoObrigatorio(especialidade, "Especialidade");

            System.out.print("*** Salário do Instrutor:             ***\n");
            double salario = sc.nextDouble(); sc.nextLine();

            Instrutor novoInstrutor = new Instrutor(cpfInstrutor, nomeInstrutor, emailInstrutor,
                    dataNascimento, sexo, especialidade, salario);

            InstrutorDB instrutorDB = new InstrutorDB();
            instrutorDB.inserir(novoInstrutor);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void listarInstrutor(Scanner sc){
        System.out.println("==================================================");
        System.out.println("*** LISTA DE INSTRUTORES             ***");
        System.out.println("==================================================");

        var lista = new InstrutorDB().listarTodos();
        if (lista.isEmpty()) {
            System.out.println("*** Nenhum instrutor cadastrado.       ***");
        } else {
            for (Instrutor i : lista) {
                // Utilizando o Polimorfismo aqui:
                System.out.println(i.obterIdentificacao());
                System.out.println("CPF: " + i.getCpfInstrutor());
                System.out.printf("Salário: R$ %.2f\n", i.getSalario());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void atualizarInstrutor(Scanner sc){
        System.out.println("==================================================");
        System.out.println("*** ATUALIZAR INSTRUTOR              ***");
        System.out.println("==================================================");
        try {
            System.out.print("** Digite o CPF do instrutor a ser atualizado:  **\n");
            String cpfBusca = sc.nextLine();
            Validador.validarCpf(cpfBusca);

            InstrutorDB db = new InstrutorDB();
            Instrutor instrutor = db.buscarPorId(cpfBusca);

            if (instrutor == null) {
                System.out.println("Erro: Instrutor com CPF " + cpfBusca + " não encontrado!");
                return;
            }

            System.out.println("Editando instrutor: " + instrutor.getNomeInstrutor());

            System.out.print("*** Novo Nome (Enter para manter):             ***\n");
            String novoNome = sc.nextLine();
            if (!novoNome.isEmpty()) instrutor.setNomeInstrutor(novoNome);

            System.out.print("*** Nova Especialidade (Enter para manter):    ***\n");
            String novaEspec = sc.nextLine();
            if (!novaEspec.isEmpty()) instrutor.setEspecialidade(novaEspec);

            db.atualizar(instrutor);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void removerInstrutor(Scanner sc){
        System.out.println("==================================================");
        System.out.println("*** REMOVER INSTRUTOR               ***");
        System.out.println("==================================================");
        try {
            System.out.print("** Digite o CPF do Instrutor que deseja remover: **\n");
            String cpfRemover = sc.nextLine();
            Validador.validarCpf(cpfRemover);
            new InstrutorDB().deletar(cpfRemover);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }
}