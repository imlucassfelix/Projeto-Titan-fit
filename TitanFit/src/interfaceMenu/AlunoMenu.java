package interfaceMenu;

import util.MetodosAluno;
import util.Validador;
import entities.Aluno;
import repositoyDB.AlunoDB;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            System.out.println("***             TITANFIT - ALUNOS              ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Aluno                          ***");
            System.out.println("[2] - Listar Alunos                            ***");
            System.out.println("[3] - Atualizar Aluno                          ***");
            System.out.println("[4] - Gerenciar Frequencia                     ***");
            System.out.println("[5] - Remover Aluno                            ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");
            
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    MetodosAluno.cadastrarAlunos();

                case 2:
                    MetodosAluno.listarAlunos();

                case 3:
                    MetodosAluno.atualizarAlunos();

                case 4:
                    System.out.println(" aguarde ...");
                    break;

                case 5:
                    MetodosAluno.removerAlunos();

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
