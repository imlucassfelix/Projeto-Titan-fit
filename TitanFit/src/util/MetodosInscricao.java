package util;
import java.util.ArrayList;
import java.util.List;
import entities.InscricaoAula;

import java.util.Scanner;

public class MetodosInscricao {
    private static Scanner sc;
    private static List<InscricaoAula> listaInscricoes = new ArrayList<>();

    public static void novaInscricao(){
        System.out.println("***               NOVA INSCRICAO               ***");
        System.out.print("***              Código da Aula:               ***");
        int codAula = sc.nextInt(); sc.nextLine();
        if (!Validador.campoObrigatorio(String.valueOf(codAula))) {
            System.out.println("***        Erro: Código é obrigatório!         ***");
        }

        System.out.println("***    Digite o CPF do aluno (11 dígitos):     ***");
        String cpfAluno = sc.nextLine();
        if (!Validador.validarCpf(cpfAluno)) {
            System.out.println("**  CPF invalido! Digite 11 digitos numericos.  **");
        }

        System.out.print("Horário (HH:mm): ");
        String horario = sc.nextLine();
        if (!Validador.validarHorario(horario)) {
            System.out.println("Erro: Horário inválido! (Use o formato HH:mm).");
            return;
        }

        System.out.print("***           Código de Inscricao:             ***");
        int codInscricao = sc.nextInt(); sc.nextLine();
        if (!Validador.campoObrigatorio(String.valueOf(codInscricao))) {
            System.out.println("***        Erro: Código é obrigatório!         ***");
        }

        // Corrigido:
        InscricaoAula novaInscricao = new InscricaoAula(codInscricao, codAula, cpfAluno, horario);
        listaInscricoes.add(novaInscricao);
        System.out.println("Inscrição realizada com sucesso!");
    }

    public static void listarTurma(){

    }

    public static void alterarTurma(){

    }

    public static void removerTurma(){

    }
}
