package interfaceMenu;

import entities.Aula;
import util.MetodosAluno;
import util.MetodosAula;
import util.Validador;
import java.util.Scanner;

public class AulaMenu {
	private Scanner sc;
	
	 public AulaMenu(Scanner sc) {
		 this.sc = sc;
	}

	 public void exibirMenu() {
	        int opcao;
	        do {
				System.out.println("");
	            System.out.println("========================================");
	            System.out.println("***       TITANFIT - AULAS           ***");
	            System.out.println("========================================");
	            System.out.println("[1] - Cadastrar Aula                 ***");
	            System.out.println("[2] - Listar Aulas                   ***");
	            System.out.println("[3] - Editar Aula/Treinos            ***");
	            System.out.println("[4] - Excluir Aula/Treinos           ***");
	            System.out.println("[0] - Voltar                         ***");
	            System.out.println("========================================");
	            System.out.print("Escolha uma opção: \n");
	            System.out.println("========================================");
	            
	            opcao = sc.nextInt(); sc.nextLine();

				switch (opcao) {
	                case 1:
						MetodosAula.cadastrarAula();

	                case 2:
	                    MetodosAula.listarAula();

	                case 3:
	                    MetodosAula.editarAula();

	                case 4:
	                    MetodosAula.excluirAula();

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
