package interfaceMenu;

import java.util.ArrayList;
import java.util.List;
import util.Validador;
import java.util.Scanner;
import entities.InscricaoAula;


public class InscricaoMenu {
    private Scanner sc;
    private List<InscricaoAula> listaInscricoes = new ArrayList<>();

    public InscricaoMenu(Scanner sc) {
            this.sc = sc;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("==================================================");
            System.out.println("***          INSCRICOES e MATRICULAS           ***");
            System.out.println("[1] - Realizar Nova Inscrição                  ***");
            System.out.println("[2] - Listar Turmas                            ***");
            System.out.println("[3] - Alterar Turma/Horario                    ***");
            System.out.println("[4] - Remover Matrícula                        ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("***               NOVA INSCRICAO               ***");
                    System.out.print("***              Código da Aula:               ***");
                    int codAula = sc.nextInt(); sc.nextLine();
                    if (!Validador.campoObrigatorio(String.valueOf(codAula))) {
                        System.out.println("***        Erro: Código é obrigatório!         ***");
                        break;
                    }

                    System.out.println("***    Digite o CPF do aluno (11 dígitos):     ***");
                    String cpfAluno = sc.nextLine();
                    if (!Validador.validarCpf(cpfAluno)) {
                        System.out.println("**  CPF invalido! Digite 11 digitos numericos.  **");
                        break;
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
                        break;
                    }

                    // Corrigido:
                    InscricaoAula novaInscricao = new InscricaoAula(codInscricao, codAula, cpfAluno, horario);
                    listaInscricoes.add(novaInscricao);
                    System.out.println("Inscrição realizada com sucesso!");
                    break;

                case 2:
                    System.out.println("Voltando...");
                    break;
                case 3:
                    System.out.println("Voltando...");
                    break;
                case 4:
                    System.out.println("Voltando...");
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
