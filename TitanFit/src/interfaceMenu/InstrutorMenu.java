package interfaceMenu;

import java.util.Scanner;

public class InstrutorMenu {
    private Scanner sc;

    public InstrutorMenu(Scanner sc) {
        this.sc = sc;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("");
            System.out.println("==================================================");
            System.out.println("*** TITANFIT - INSTRUTOR            ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Instrutor                      ***");
            System.out.println("[2] - Listar Instrutores                       ***");
            System.out.println("[3] - Atualizar Instrutor                      ***");
            System.out.println("[4] - Remover Instrutor                        ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");

            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    util.MetodosInstrutor.cadastrarInstrutor();
                    break;
                case 2:
                    util.MetodosInstrutor.listarInstrutor();
                    break;
                case 3:
                    util.MetodosInstrutor.atualizarInstrutor();
                    break;
                case 4:
                    util.MetodosInstrutor.removerInstrutor();
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