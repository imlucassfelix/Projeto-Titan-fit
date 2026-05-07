package interfaceMenu;

import entities.Fidelidade;
import entities.Plano;
import repositoyDB.FidelidadeDB;
import repositoyDB.PlanoDB;
import util.MetodosPlano;
import util.Validador;

import java.util.ArrayList;
import java.util.List;
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
                    MetodosPlano.cadastrarPlano();
                    break;

                case 2:
                    MetodosPlano.listarPlanos();
                    break;

                case 3:
                    MetodosPlano.alterarPlano();
                    break;

                case 4:
                    MetodosPlano.removerPlano();
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