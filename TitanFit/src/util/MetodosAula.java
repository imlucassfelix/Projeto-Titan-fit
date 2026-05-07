package util;

import entities.Aula;

import java.util.Scanner;

public class MetodosAula {
    private static Scanner sc;

    public static void cadastrarAula(){
        System.out.println("***               CADASTRAR AULA               ***");
        System.out.print("***              Código da Aula:               ***");
        int codAula = sc.nextInt(); sc.nextLine();
        if (!Validador.campoObrigatorio(String.valueOf(codAula))) {
            System.out.println("***        Erro: Código é obrigatório!         ***");
        }

        System.out.print("***            Digite nome da aula:            ***");
        String nomeAula = sc.nextLine();
        if (!Validador.campoObrigatorio(nomeAula)) {
            System.out.println("**     Erro: O nome da aula é obrigatória!      **");
        }

        System.out.print("***                Modalidade:                 ***");
        String modalidade = sc.nextLine();
        if (!Validador.campoObrigatorio(modalidade)) {
            System.out.println("**  Erro: O nome da modalidade  é obrigatório!  **");
        }

        System.out.print("***             Descrição da Aula:             ***");
        String descricaoAula = sc.nextLine();
        if (!Validador.campoObrigatorio(descricaoAula)) {
            System.out.println("**    Erro: descricao da aula é obrigatória!    **");
        }

        System.out.print("***            Capacidade Máxima:              ***");
        int capacidadeMaxima = sc.nextInt(); sc.nextLine();
        if (!Validador.validarCapacidade(capacidadeMaxima)) {
            System.out.println("*  Erro: A capacidade deve estar entre 1 e 50.   *");
        }

        System.out.print("***       CPF do Instrutor responsável:        ***");
        String cpfInstrutor = sc.nextLine();
        if (!Validador.validarCpf(cpfInstrutor)) {
            System.out.println("**  CPF inválido! Digite 11 dígitos numéricos.  **");
        }

        Aula novaAula = new Aula(codAula, nomeAula, capacidadeMaxima, descricaoAula, modalidade, cpfInstrutor);
        System.out.println("***      Aula cadastrada com sucesso!          ***");
    }

    public static void listarAula(){

    }

    public static void editarAula(){

    }

    public static void excluirAula(){

    }
}
