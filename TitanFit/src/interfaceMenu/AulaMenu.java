package interfaceMenu;

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
	            System.out.println("[3] - Gerenciar Frequencia           ***");
	            System.out.println("[4] - Gerenciar Treinos              ***");
	            System.out.println("[0] - Voltar                         ***");
	            System.out.println("========================================");
	            System.out.print("Escolha uma opção: \n");
	            System.out.println("========================================");
	            
	            opcao = sc.nextInt();
	            
	            switch (opcao) {
	                case 1:
	                    System.out.println(" Cadastro em breve");
	                    break;
	                case 2:
	                    System.out.println(" Listagem em breve");
	                    break;
	                case 3:
	                    System.out.println(" Resgistrando em breve");
	                    break;
	                case 4:
	                    System.out.println(" Treinos em breve");
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
