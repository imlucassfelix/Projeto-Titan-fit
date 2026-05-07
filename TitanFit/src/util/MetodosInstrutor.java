package util;

import entities.Instrutor;
import repositoyDB.InstrutorDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MetodosInstrutor {
    private static Scanner sc = new Scanner(System.in);

    public static void cadastrarInstrutor(){
        System.out.println("==================================================");
        System.out.println("*** CADASTRAR INSTRUTOR               ***");
        System.out.println("==================================================");

        System.out.print("*** CPF do Instrutor:              ***\n");
        String cpfInstrutor = sc.nextLine();
        if (!Validador.validarCpf(cpfInstrutor)) {
            System.out.println("** CPF inválido! Digite 11 dígitos numéricos.  **");
            return;
        }

        System.out.print("*** Nome completo:               ***\n");
        String nomeInstrutor = sc.nextLine();
        if (!Validador.campoObrigatorio(nomeInstrutor)) {
            System.out.println("** Erro: O nome é obrigatório!             **");
            return;
        }

        System.out.print("*** Email:                   ***\n");
        String emailInstrutor = sc.nextLine();
        if (!Validador.validarEmail(emailInstrutor)) {
            System.out.println("** Email inválido! Digite um email válido.   **");
            return;
        }

        System.out.print("*** Data de nascimento (dd/mm/aaaa):  ***\n");
        String dataNascStr = sc.nextLine();
        if (!Validador.validarData(dataNascStr)) {
            System.out.println("** Data inválida! Use o formato dd/mm/aaaa.  **");
            return;
        }
        LocalDate dataNascimento = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("*** Sexo (M/F/I):               ***\n");
        String sexo = sc.nextLine();
        if (!Validador.validarSexo(sexo)) {
            System.out.println("** Sexo inválido! Digite M, F ou I.        **");
            return;
        }

        System.out.print("*** Especialidade:               ***\n");
        String especialidade = sc.nextLine();
        if (!Validador.campoObrigatorio(especialidade)) {
            System.out.println("** Erro: A especialidade é obrigatória!      **");
            return;
        }

        System.out.print("*** Salário do Instrutor:             ***\n");
        double salario = sc.nextDouble(); sc.nextLine();

        Instrutor novoInstrutor = new Instrutor(cpfInstrutor, nomeInstrutor, emailInstrutor,
                dataNascimento, sexo, especialidade, salario);

        InstrutorDB instrutorDB = new InstrutorDB();
        instrutorDB.inserir(novoInstrutor);
    }

    public static void listarInstrutor(){
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

    public static void atualizarInstrutor(){
        System.out.println("==================================================");
        System.out.println("*** ATUALIZAR INSTRUTOR              ***");
        System.out.println("==================================================");
        System.out.print("** Digite o CPF do instrutor a ser atualizado:  **\n");
        String cpfBusca = sc.nextLine();

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
    }

    public static void removerInstrutor(){
        System.out.println("==================================================");
        System.out.println("*** REMOVER INSTRUTOR               ***");
        System.out.println("==================================================");
        System.out.print("** Digite o CPF do Instrutor que deseja remover: **\n");
        String cpfRemover = sc.nextLine();
        new InstrutorDB().deletar(cpfRemover);
    }
}