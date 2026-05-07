package util;

import entities.Aluno;
import entities.Frequenta;
import repositoyDB.AlunoDB;
import repositoyDB.FrequentaDB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MetodosAluno {
    private static Scanner sc = new Scanner(System.in);

    public static void cadastrarAlunos(){
        System.out.println("==================================================");
        System.out.println("*** CADASTRO DE ALUNOS             ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o nome do aluno:          ***\n");
        String nomeAluno = sc.nextLine();
        if (!Validador.campoObrigatorio(nomeAluno)) {
            System.out.println("*** Nome nao pode ser vazio!           ***");
            return;
        }

        System.out.print("*** Digite o CPF do aluno (11 dígitos):     ***\n");
        String cpfAluno = sc.nextLine();
        if (!Validador.validarCpf(cpfAluno)) {
            System.out.println("** CPF invalido! Digite 11 digitos numericos.  **");
            return;
        }

        System.out.print("*** Sexo (M/F/I):                 ***\n");
        String sexo = sc.nextLine();
        if (!Validador.validarSexo(sexo)) {
            System.out.println("*** Sexo invalido! Digite M, F ou I.      ***");
            return;
        }

        System.out.print("*** Data de nascimento (dd/mm/aaaa):      ***\n");
        String dataNascStr = sc.nextLine();
        if (!Validador.validarData(dataNascStr)) {
            System.out.println("** Data invalida! Use o formato dd/mm/aaaa.   **");
            return;
        }
        LocalDate dataNascimentoAluno = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("*** Telefone:                  ***\n");
        String telefoneAluno = sc.nextLine();
        if (!Validador.validarTelefone(telefoneAluno)) {
            System.out.println("* Telefone invalido! Use 11 digitos numericos.  *");
            return;
        }

        System.out.print("*** Digite um endereço de e-mail:        ***\n");
        String emailAluno = sc.nextLine();
        if (!Validador.validarEmail(emailAluno)) {
            System.out.println("*** Email invalido! Digite um email valido.   ***");
            return;
        }

        LocalDate dataMatricula = LocalDate.now();

        Aluno novoAluno = new Aluno(cpfAluno, nomeAluno, emailAluno, telefoneAluno, dataNascimentoAluno, dataMatricula, sexo);
        new AlunoDB().inserir(novoAluno);
    }

    public static void listarAlunos(){
        System.out.println("==================================================");
        System.out.println("*** LISTA DE ALUNOS               ***");
        System.out.println("==================================================");
        var lista = new AlunoDB().listarTodos();
        if (lista.isEmpty()) {
            System.out.println("*** Nenhum aluno cadastrado.          ***");
        } else {
            for (Aluno a : lista) {
                // Aqui estamos usando o Polimorfismo da classe Pessoa/Aluno
                System.out.println(a.obterIdentificacao());
                System.out.println("Email: " + a.getEmailAluno());
                System.out.println("Telefone: " + a.getTelefoneAluno());
                System.out.println("Matrícula: " + a.getDataMatricula());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void atualizarAlunos(){
        System.out.println("==================================================");
        System.out.println("*** ATUALIZAR DADOS DO ALUNO          ***");
        System.out.println("==================================================");
        System.out.print("* Digite o CPF do aluno que deseja atualizar:   *\n");
        String cpfBusca = sc.nextLine();

        AlunoDB db = new AlunoDB();
        Aluno alunoParaAtualizar = db.buscarPorId(cpfBusca);

        if (alunoParaAtualizar == null) {
            System.out.println("Erro: Aluno com CPF " + cpfBusca + " não encontrado!");
        } else {
            System.out.println("Editando aluno: " + alunoParaAtualizar.getNomeAluno());

            System.out.print("*** Novo Nome (ou aperte Enter para manter):   ***\n");
            String novoNome = sc.nextLine();
            if (!novoNome.isEmpty()) alunoParaAtualizar.setNomeAluno(novoNome);

            System.out.print("*** Novo Email (ou aperte Enter para manter):  ***\n");
            String novoEmail = sc.nextLine();
            if (!novoEmail.isEmpty()) alunoParaAtualizar.setEmailAluno(novoEmail);

            System.out.print("** Novo Telefone (ou aperte Enter para manter): **\n");
            String novoTelefone = sc.nextLine();
            if (!novoTelefone.isEmpty()) alunoParaAtualizar.setTelefoneAluno(novoTelefone);

            db.atualizar(alunoParaAtualizar);
        }
    }

    public static void frequenciaAlunos(){
        System.out.println("==================================================");
        System.out.println("*** REGISTRAR FREQUÊNCIA              ***");
        System.out.println("==================================================");

        System.out.print("*** Digite o CPF do aluno:                     ***\n");
        String cpfAluno = sc.nextLine();

        System.out.print("*** Digite o Código da Aula:                   ***\n");
        int codAula = sc.nextInt(); sc.nextLine();

        LocalDate dataHoje = LocalDate.now();
        LocalTime horaAgora = LocalTime.now();

        Frequenta freq = new Frequenta(cpfAluno, codAula, dataHoje, horaAgora);
        new FrequentaDB().inserir(freq);
    }

    public static void removerAlunos(){
        System.out.println("==================================================");
        System.out.println("*** REMOVER ALUNO                 ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o CPF do aluno que deseja remover:  ***\n");
        String cpfRemover = sc.nextLine();
        new AlunoDB().deletar(cpfRemover);
    }
}