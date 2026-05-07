package util;

import entities.Instrutor;
import repositoyDB.AlunoDB;
import repositoyDB.InstrutorDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MetodosInstrutor {
    private static Scanner sc;

    public static void cadastrarInstrutor(){
        System.out.println("==================================================");
        System.out.println("***          CADASTRAR INSTRUTOR               ***");
        System.out.println("==================================================");

        System.out.print("***             CPF do Instrutor:              ***");
        String cpfInstrutor = sc.nextLine();
        if (!Validador.validarCpf(cpfInstrutor)) {
            System.out.println("**  CPF inválido! Digite 11 dígitos numéricos.  **");
        }

        System.out.print("***               Nome completo:               ***");
        String nomeInstrutor = sc.nextLine();
        if (!Validador.campoObrigatorio(nomeInstrutor)) {
            System.out.println("**     Erro: O nome é obrigatório!             **");
        }

        System.out.print("***                   Email:                   ***");
        String emailInstrutor = sc.nextLine();
        if (!Validador.validarEmail(emailInstrutor)) {
            System.out.println("**   Email inválido! Digite um email válido.   **");
        }

        System.out.print("***          Data de nascimento (dd/mm/aaaa):  ***");
        String dataNascStr = sc.nextLine();
        if (!Validador.validarData(dataNascStr)) {
            System.out.println("**   Data inválida! Use o formato dd/mm/aaaa.  **");
        }
        LocalDate dataNascimento = LocalDate.parse(dataNascStr,
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("***                Sexo (M/F/I):               ***");
        String sexo = sc.nextLine();
        if (!Validador.validarSexo(sexo)) {
            System.out.println("**     Sexo inválido! Digite M, F ou I.        **");
        }

        System.out.print("***               Especialidade:               ***");
        String especialidade = sc.nextLine();
        if (!Validador.campoObrigatorio(especialidade)) {
            System.out.println("**   Erro: A especialidade é obrigatória!      **");
        }

        System.out.print("***          Salário do Instrutor:             ***");
        double salario = sc.nextDouble(); sc.nextLine();

        Instrutor novoInstrutor = new Instrutor(cpfInstrutor, nomeInstrutor, emailInstrutor,
                dataNascimento, sexo, especialidade, salario);

        InstrutorDB instrutorDB = new InstrutorDB();
        instrutorDB.inserir(novoInstrutor);
        System.out.println("***     Instrutor cadastrado com sucesso!      ***");
    }

    public static void listarInstrutor(){
    }

    public static void atualizarInstrutor(){
    }

    public static void removerInstrutor(){
            System.out.print("** Digite CPF do Instrutor que deseja remover:  **");
            String cpfRemover = sc.nextLine();
            InstrutorDB RemoverDB = new InstrutorDB();
            RemoverDB.deletar(cpfRemover);

    }

}
