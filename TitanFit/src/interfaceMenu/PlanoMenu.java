package interfaceMenu;

/**
 * Submenu de gerenciamento de planos no Titan Fit.
 *
 * @author Lucas Felix
 * @version 1.0
 */

import util.MetodosPlano;

import java.util.Scanner;

public class PlanoMenu {
    private Scanner sc;

    public PlanoMenu(Scanner sc) {
        this.sc = sc;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("");
            System.out.println("==================================================");
            System.out.println("***             TITANFIT - PLANOS              ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Plano                          ***");
            System.out.println("[2] - Listar Planos                            ***");
            System.out.println("[3] - Alterar Plano                            ***");
            System.out.println("[4] - Remover Plano                            ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    MetodosPlano.cadastrarPlano(sc);
                    break;

                case 2:
                    MetodosPlano.listarPlanos(sc);
                    break;

                case 3:
                    MetodosPlano.alterarPlano(sc);
                    break;

                case 4:
                    MetodosPlano.removerPlano(sc);
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