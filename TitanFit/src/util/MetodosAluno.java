package util;

import entities.Aluno;
import repositoyDB.AlunoDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class MetodosAluno {
    private static Scanner sc;

    public static void cadastrarAlunos(){
        System.out.println("***             CADASTRO DE ALUNOS             ***");
        System.out.println("***           Digite o nome do aluno:          ***");
        String nomeAluno = sc.nextLine();
        if
        (!Validador.campoObrigatorio(nomeAluno)) {
            System.out.println("***         Nome nao pode ser vazio!           ***");
        }

        System.out.println("***    Digite o CPF do aluno (11 dígitos):     ***");
        String cpfAluno = sc.nextLine();
        if (!Validador.validarCpf(cpfAluno)) {
            System.out.println("**  CPF invalido! Digite 11 digitos numericos.  **");
        }

        System.out.println("***              Sexo (M/F/I):                 ***");
        String sexo = sc.nextLine();
        if (!Validador.validarSexo(sexo)) {
            System.out.println("***      Sexo invalido! Digite M, F ou I.      ***");
        }

        System.out.println("***      Data de nascimento (dd/mm/aaaa):      ***");
        String dataNascStr = sc.nextLine();
        if (!Validador.validarData(dataNascStr)) {
            System.out.println("**   Data invalida! Use o formato dd/mm/aaaa.   **");
        }
        LocalDate dataNascimentoAluno = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("***                 Telefone:                  ***");
        String telefoneAluno = sc.nextLine();
        if (!Validador.validarTelefone(telefoneAluno)) {
            System.out.println("*  Telefone invalido! Use 11 digitos numericos.  *");
        }

        System.out.println("***       Digite um endereço de e-mail:        ***");
        String emailAluno = sc.nextLine();
        if (!Validador.validarEmail(emailAluno)) {
            System.out.println("***  Email invalido! Digite um email valido.   ***");
        }

        LocalDate dataMatricula = LocalDate.now();

        Aluno novoAluno = new Aluno( cpfAluno, nomeAluno, emailAluno, telefoneAluno, dataNascimentoAluno, dataMatricula, sexo);

        System.out.println("***              Aluno criado:                 ***\n" + novoAluno);
        AlunoDB alunoDB = new AlunoDB();
        alunoDB.inserir(novoAluno);
    }

    public static void listarAlunos(){
        System.out.println("***              LISTA DE ALUNOS               ***");
        AlunoDB ListarDB = new AlunoDB();
        var lista = ListarDB.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("***          Nenhum aluno cadastrado.          ***");
        } else {
            for (Aluno a : lista) {
                System.out.println("Nome: " + a.getNomeAluno());
                System.out.println("CPF: " + a.getCpfAluno());
                System.out.println("Email: " + a.getEmailAluno());
                System.out.println("Telefone: " + a.getTelefoneAluno());
                System.out.println("Nascimento: " + a.getDataNascimento());
                System.out.println("Sexo: \n" + a.getSexo());
            }
        }
    }
    public static void atualizarAlunos(){
        System.out.println("***          ATUALIZAR DADOS DO ALUNO          ***");
        System.out.print("*  Digite o CPF do aluno que deseja atualizar:   *");
        String cpfBusca = sc.nextLine();

        // 1. Buscamos o aluno no banco para garantir que ele existe
        Aluno alunoParaAtualizar = new AlunoDB().buscarPorId(cpfBusca);

        if (alunoParaAtualizar == null) {
            System.out.println("Erro: Aluno com CPF " + cpfBusca + " não encontrado!");
        } else {
            // 2. Pedimos os novos dados (mantendo o que o utilizador não quiser mudar)
            System.out.println("Editando aluno: " + alunoParaAtualizar.getNomeAluno());

            System.out.print("*** Novo Nome (ou aperte Enter para manter):   ***");
            String novoNome = sc.nextLine();
            if (!novoNome.isEmpty()) alunoParaAtualizar.setNomeAluno(novoNome);

            System.out.print("*** Novo Email (ou aperte Enter para manter):  ***");
            String novoEmail = sc.nextLine();
            if (!novoEmail.isEmpty()) alunoParaAtualizar.setEmailAluno(novoEmail);

            System.out.print("** Novo Telefone (ou aperte Enter para manter): **");
            String novoTelefone = sc.nextLine();
            if (!novoTelefone.isEmpty()) alunoParaAtualizar.setTelefoneAluno(novoTelefone);

            // 3. Atualizamos no banco de dados
            new AlunoDB().atualizar(alunoParaAtualizar);
            System.out.println("***       Dados atualizados com sucesso!       ***");
        }
    }

    public static void frequenciaAlunos(){

    }

    public static void removerAlunos(){
        System.out.print("*** Digite o CPF do aluno que deseja remover:  ***");
        String cpfRemover = sc.nextLine();
        AlunoDB RemoverDB = new AlunoDB();
        RemoverDB.deletar(cpfRemover);
    }
}
