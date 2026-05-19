package interfaceMenu;

/**
 * Submenu de gerenciamento de alunos no Titan Fit.
 *
 * <p>Exibe as opcoes: cadastrar, listar, atualizar, gerenciar
 * frequencia e remover alunos, delegando para {@link util.MetodosAluno}.</p>
 *
 * @author Lucas Rodrigues
 * @version 1.0
 */

// ── AulaMenu.java ────────────────────────────────────────────
/**
 * Submenu de gerenciamento de aulas e treinos no Titan Fit.
 *
 * @author Ryan Vinicius
 * @version 1.0
 */

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
            System.out.println("=======================================================");
            System.out.println("***                TITANFIT - ALUNOS                ***");
            System.out.println("=======================================================");
            System.out.println("[1] - Cadastrar Aluno                               ***");
            System.out.println("[2] - Listar Alunos                                 ***");
            System.out.println("[3] - Atualizar Aluno                               ***");
            System.out.println("[4] - Gerenciar Frequência                          ***");
            System.out.println("[5] - Remover Aluno                                 ***");
            System.out.println("[0] - Voltar                                        ***");
            System.out.println("=======================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("=======================================================");

            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    MetodosAluno.cadastrarAlunos(sc);
                    break;

                case 2:
                    MetodosAluno.listarAlunos(sc);
                    break;

                case 3:
                    MetodosAluno.atualizarAlunos(sc);
                    break;

                case 4:
                    MetodosAluno.frequenciaAlunos(sc);
                    break;

                case 5:
                    MetodosAluno.removerAlunos(sc);
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