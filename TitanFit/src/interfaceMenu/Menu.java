package interfaceMenu;

import java.util.Scanner;

public class Menu {
	private Scanner sc;

	public Menu(Scanner sc) {
		this.sc = sc;
	}

	public void exibirMenu() {
		int opcao;
		//principal
		do {
            System.out.println("");
            System.out.println("========================================");
            System.out.println("***        TITANFIT - HOME           ***");
            System.out.println("========================================");
            System.out.println("[1] - Gerenciar Alunos               ***");
            System.out.println("[2] - Gerenciar Instrutores          ***");
            System.out.println("[3] - Planos e Matrículas            ***");
            System.out.println("[4] - Gestão de Aulas e Treinos      ***");
            System.out.println("[5] - Inventário de Equipamentos     ***");
            System.out.println("[0] - Sair                           ***");
            System.out.println("========================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("========================================");

            opcao = sc.nextInt();  sc.nextLine();
            sc.nextLine();

            switch (opcao) {

                case 1:
                     new AlunoMenu(sc).exibirMenu();
                    break;
                case 2:
                	new InstrutorMenu(sc).exibirMenu();
                    break;
                case 3:
                	new PlanoMenu(sc).exibirMenu();
                    break;
                case 4:
                	new AulaMenu(sc).exibirMenu();
                    break;
                case 5:
                	new EquipamentosMenu(sc).exibirMenu();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
            System.out.println();
        } while (opcao != 0);
	}
}
