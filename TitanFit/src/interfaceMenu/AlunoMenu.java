package interfaceMenu;

import util.MetodosAluno;
import java.util.Scanner;

public class AlunoMenu {
    private Scanner sc;

    public AlunoMenu(Scanner sc) {
        this.sc = sc;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("");
            System.out.println("==================================================");
            System.out.println("*** TITANFIT - ALUNOS              ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Aluno                          ***");
            System.out.println("[2] - Listar Alunos                            ***");
            System.out.println("[3] - Atualizar Aluno                          ***");
            System.out.println("[4] - Gerenciar Frequência                     ***");
            System.out.println("[5] - Remover Aluno                            ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");

            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    MetodosAluno.cadastrarAlunos();
                    break;
                case 2:
                    MetodosAluno.listarAlunos();
                    break;
                case 3:
                    MetodosAluno.atualizarAlunos();
                    break;
                case 4:
                    MetodosAluno.frequenciaAlunos();
                    break;
                case 5:
                    MetodosAluno.removerAlunos();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
            System.out.println();
        } while (opcao != 0);
    }
}